package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.UserCalendar;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;
import cn.mulanbay.pms.persistent.service.BudgetService;
import cn.mulanbay.pms.persistent.service.UserCalendarService;
import cn.mulanbay.pms.web.bean.request.fund.BudgetSearch;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 预算提醒
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BudgetRemindJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(BudgetRemindJob.class);

    BudgetRemindJobPara para;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        //step 1:获取需要提醒的预算列表
        BudgetSearch sf = new BudgetSearch();
        sf.setStatus(CommonStatus.ENABLE);
        sf.setRemind(true);
        BaseService baseService = BeanFactoryUtil.getBean(BaseService.class);
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(Budget.class);
        List<Budget> list = baseService.getBeanList(pr);
        BudgetHandler budgetHandler = BeanFactoryUtil.getBean(BudgetHandler.class);
        BudgetService budgetService = BeanFactoryUtil.getBean(BudgetService.class);
        //step 2:根据日志查询是否已经完成
        Date bussDay = this.getBussDay();
        for (Budget bd : list) {
            String bussKey = budgetHandler.createBussKey(bd.getPeriod(), bussDay);
            boolean b = budgetService.isBudgetLogExit(bussKey, bd.getUserId().longValue(), null, bd.getId());
            if (!b) {
                //step 3:发送提醒信息
                handleNotifyBudget(bd, bussKey);
            }
        }
        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        tr.setComment("一共统计了" + list.size() + "个预算提醒");
        return tr;
    }

    private void handleNotifyBudget(Budget bd, String bussKey) {
        try {
            BudgetHandler budgetHandler = BeanFactoryUtil.getBean(BudgetHandler.class);
            Integer n = budgetHandler.getLeftDays(bd, new Date());
            if (n != null && n > para.getMinDays()) {
                logger.debug("预算[" + bd.getName() + "]还未到提醒时间。");
                return;
            }
            String title = "预算[" + bd.getName() + "]未完成";
            StringBuffer content = new StringBuffer();

            content.append("预算[" + bd.getName() + "]在" + bussKey + "未完成。");
            content.append("预算金额:" + PriceUtil.changeToString(2, bd.getAmount()) + "元。");
            content.append("请及时完成且记录日志。");
            PmsNotifyHandler pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
            //发送消息
            String cc = content.toString();
            Long messageId = pmsNotifyHandler.addNotifyMessage(PmsErrorCode.BUDGET_UN_COMPLETED, title, cc,
                    bd.getUserId(), null);
            //增加积分
            RewardPointsHandler rewardPointsHandler = BeanFactoryUtil.getBean(RewardPointsHandler.class);
            //TODO 目前预算与预算日志无法区分，要么RewardSource区分不同的
            rewardPointsHandler.rewardPoints(bd.getUserId(), para.getScore(), bd.getId(), RewardSource.BUDGET, null, messageId);
            this.addToUserCalendar(bd, bussKey, messageId, cc);
        } catch (Exception e) {
            logger.error("处理预算统计消息提醒异常");
        }

    }

    /**
     * 更新到用户日历
     *
     * @param bd
     */
    private void addToUserCalendar(Budget bd, String bussKey, Long messageId, String content) {
        try {
            UserCalendarService userCalendarService = BeanFactoryUtil.getBean(UserCalendarService.class);
            String bussIdentityKey = bussKey;
            UserCalendar uc = userCalendarService.getUserCalendar(bd.getUserId(), bussIdentityKey, new Date());
            if (uc != null) {
                userCalendarService.updateUserCalendarToDate(uc, new Date(), messageId);
            } else {
                uc = new UserCalendar();
                uc.setUserId(bd.getUserId());
                uc.setTitle("及时完成预算");
                uc.setContent(content);
                uc.setDelayCounts(0);
                uc.setBussDay(DateUtil.getDate(0));
                uc.setExpireTime(DateUtil.getLastDayOfMonth(new Date()));
                uc.setBussIdentityKey(bussIdentityKey);
                uc.setSourceType(UserCalendarSource.BUDGET);
                uc.setSourceId("B_" + bd.getId().toString());
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
        if (para == null) {
            para = new BudgetRemindJobPara();
        }
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return BudgetRemindJobPara.class;
    }
}
