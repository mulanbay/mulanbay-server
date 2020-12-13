package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.BudgetLog;
import cn.mulanbay.pms.persistent.domain.UserCalendar;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;
import cn.mulanbay.pms.persistent.service.BudgetService;
import cn.mulanbay.pms.persistent.service.UserCalendarService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预算执行统计
 * 统计月度预算的执行情况
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BudgetExecStatJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(BudgetExecStatJob.class);

    private BudgetExecStatJobPara para;

    BudgetService budgetService;

    BudgetHandler budgetHandler;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        //step 1:查询预算
        budgetService = BeanFactoryUtil.getBean(BudgetService.class);
        budgetHandler = BeanFactoryUtil.getBean(BudgetHandler.class);

        List<Budget> list = budgetService.getActiveUserBudget(null, null);
        if (list.isEmpty()) {
            tr.setExecuteResult(JobExecuteResult.SKIP);
            tr.setComment("没有需要统计的预算");
            return tr;
        }
        Date bussDay = this.getBussDay();
        String bussKey = budgetHandler.createBussKey(para.getPeriod(), bussDay);
        boolean isRedo = this.isRedo();
        List<Budget> usList = new ArrayList<>();
        long cuserId = list.get(0).getUserId();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            Budget ubs = list.get(i);
            if (ubs.getUserId().longValue() == cuserId) {
                usList.add(ubs);
            } else {
                //当前用户结束
                handle(usList, bussKey, isRedo, bussDay);
                usList = new ArrayList<>();
                usList.add(ubs);
                cuserId = ubs.getUserId();
            }
            if (i == n - 1) {
                handle(usList, bussKey, isRedo, bussDay);
            }
        }
        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        tr.setComment("一共统计了" + list.size() + "个用户的预算执行情况");
        return tr;
    }

    private void handle(List<Budget> usList, String bussKey, boolean isRedo, Date bussDay) {
        try {
            Long userId = usList.get(0).getUserId();
            boolean b = budgetService.isBudgetLogExit(bussKey, userId, null, null);
            //判断是否超期,如果是新的
            if (!b) {
                BudgetLog bl = budgetHandler.statAndSaveBudgetLog(usList, userId, bussDay, bussKey, isRedo, para.getPeriod(), false);
                handleConsumeCheck(bl);
            } else {
                logger.debug("用户[" + userId + "]的bussKey=" + bussKey + "已经存在，无需再统计");
            }
        } catch (Exception e) {
            logger.error("处理bussKey=" + bussKey + "的预算异常", e);
        }
    }

    private void handleConsumeCheck(BudgetLog bl) {
        try {
            //算积分及加入用户任务
            String title = "[" + bl.getPeriodName() + "]预算统计：" + bl.getBussKey();
            StringBuffer content = new StringBuffer();
            double totalConsume = bl.getNcAmount() + bl.getBcAmount() + bl.getTrAmount();
            int code;
            int rewards;
            if (totalConsume - bl.getBudgetAmount() >= 0.01) {
                //超出预算
                content.append(bl.getBussKey() + "预算没控制,实际消费超出预算。\n");
                code = PmsErrorCode.BUDGET_CHECK_OVER;
                rewards = para.getOverBudgetScore();
            } else {
                content.append("实际消费在预算之内。\n");
                code = PmsErrorCode.BUDGET_CHECK_LESS;
                rewards = para.getLessBudgetScore();
            }
            content.append("预算金额:" + PriceUtil.changeToString(2, bl.getBudgetAmount()) + "元,");
            content.append("实际消费:" + PriceUtil.changeToString(2, totalConsume) + "元。\n");
            content.append("其中普通消费:" + PriceUtil.changeToString(2, bl.getNcAmount()) + "元,");
            content.append("突发消费:" + PriceUtil.changeToString(2, bl.getBcAmount()) + "元,");
            content.append("看病消费:" + PriceUtil.changeToString(2, bl.getTrAmount()) + "元。");
            PmsNotifyHandler pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
            //发送消息
            String cc = content.toString();
            Long messageId = pmsNotifyHandler.addNotifyMessage(code, title, cc,
                    bl.getUserId(), null, "/fund/budgetDetail.html");
            //增加积分
            RewardPointsHandler rewardPointsHandler = BeanFactoryUtil.getBean(RewardPointsHandler.class);
            rewardPointsHandler.rewardPoints(bl.getUserId(), rewards, bl.getId(), RewardSource.BUDGET_LOG, null, messageId);
            if (code == PmsErrorCode.BUDGET_CHECK_OVER) {
                this.addToUserCalendar(bl, messageId, cc);
            }
        } catch (Exception e) {
            logger.error("处理预算统计消息提醒异常");
        }

    }

    /**
     * 更新到用户日历
     *
     * @param bl
     */
    private void addToUserCalendar(BudgetLog bl, Long messageId, String content) {
        try {
            UserCalendarService userCalendarService = BeanFactoryUtil.getBean(UserCalendarService.class);
            String bussIdentityKey = bl.getBussKey();
            UserCalendar uc = userCalendarService.getUserCalendar(bl.getUserId(), bussIdentityKey, new Date());
            if (uc != null) {
                userCalendarService.updateUserCalendarToDate(uc, new Date(), messageId);
            } else {
                uc = new UserCalendar();
                uc.setUserId(bl.getUserId());
                uc.setTitle("注意花费");
                uc.setContent(content);
                uc.setDelayCounts(0);
                uc.setBussDay(DateUtil.getDate(0));
                uc.setExpireTime(DateUtil.getLastDayOfMonth(new Date()));
                uc.setBussIdentityKey(bussIdentityKey);
                uc.setSourceType(UserCalendarSource.BUDGET);
                uc.setSourceId("BL_" + bl.getId().toString());
                uc.setMessageId(messageId);
                userCalendarService.addUserCalendarToDate(uc);
            }
        } catch (Exception e) {
            logger.error("添加到用户日历异常", e);
        }

    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        para = this.getTriggerParaBean();
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return BudgetExecStatJobPara.class;
    }
}
