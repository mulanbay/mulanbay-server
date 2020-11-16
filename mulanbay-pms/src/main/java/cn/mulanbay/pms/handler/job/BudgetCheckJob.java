package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.bean.BudgetAmountBean;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.BudgetLog;
import cn.mulanbay.pms.persistent.domain.BudgetTimeline;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.service.BudgetService;
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
 * 每天定时检查月度、年度预算
 * 确保预算在可控范围内
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BudgetCheckJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(BaseAbstractSharesJob.class);

    private BudgetCheckJobPara para;

    BudgetService budgetService;

    BudgetHandler budgetHandler;

    PmsNotifyHandler pmsNotifyHandler;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        budgetService = BeanFactoryUtil.getBean(BudgetService.class);
        budgetHandler = BeanFactoryUtil.getBean(BudgetHandler.class);
        pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);

        Date bussDay = this.getBussDay();
        //总天数
        int tds;
        //已经过去天数
        int pds;
        if (para.getPeriod() == PeriodType.MONTHLY) {
            //当月总天数
            tds = DateUtil.getMonthDays(bussDay);
            //当月已经过去几天
            pds = DateUtil.getDayOfMonth(bussDay);
        } else {
            //当年总天数
            tds = DateUtil.getDays(Integer.valueOf(DateUtil.getYear(bussDay)));
            //当年已经过去几天
            pds = DateUtil.getDayOfYear(bussDay);
        }
        boolean notifyMessge = true;

        if (pds * 100 / tds < para.getCheckFromRate()) {
            notifyMessge = false;
        }

        List<Budget> list = budgetService.getActiveUserBudget(null, null);
        if (list.isEmpty()) {
            tr.setExecuteResult(JobExecuteResult.SKIP);
            tr.setComment("没有需要检查的预算");
            return tr;
        }
        List<Budget> usList = new ArrayList<>();
        long cuserId = list.get(0).getUserId();
        int n = list.size();
        int users = 0;
        for (int i = 0; i < n; i++) {
            Budget ubs = list.get(i);
            if (ubs.getUserId().longValue() == cuserId) {
                usList.add(ubs);
            } else {
                //当前用户结束
                handle(usList, bussDay, tds, pds, notifyMessge);
                usList = new ArrayList<>();
                usList.add(ubs);
                cuserId = ubs.getUserId();
                users++;
            }
            if (i == n - 1) {
                handle(usList, bussDay, tds, pds, notifyMessge);
            }
        }
        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        tr.setComment("一共检查了" + (users + 1) + "个用户的预算");
        return tr;
    }

    private void handle(List<Budget> usList, Date bussDay, int tds, int pds, boolean notifyMessge) {
        if (para.getPeriod() == PeriodType.MONTHLY) {
            handleMonth(usList, bussDay, tds, pds, notifyMessge);
        } else {
            handleYear(usList, bussDay, tds, pds, notifyMessge);
        }
    }


    private void handleMonth(List<Budget> usList, Date bussDay, int tds, int pds, boolean notifyMessge) {
        try {
            Long userId = usList.get(0).getUserId();
            BudgetAmountBean bab = budgetHandler.calcBudgetAmount(usList, bussDay);
            double budgetAmount = bab.getMonthBudget();
            PeriodType period = PeriodType.MONTHLY;
            Date[] ds = budgetHandler.getDateRange(period, bussDay, false);
            BudgetLog bl = budgetHandler.statBudget(userId, budgetAmount, ds[0], ds[1], null, false, period);
            //增加时间线流水
            BudgetTimeline timeline = new BudgetTimeline();
            BeanCopy.copyProperties(bl, timeline);
            timeline.setCreatedTime(new Date());
            timeline.setLastModifyTime(null);
            timeline.setBussDay(bussDay);
            timeline.setTotalDays(tds);
            timeline.setPassDays(pds);
            String bussKey = "MS" + budgetHandler.createBussKey(period, bussDay);
            timeline.setBussKey(bussKey);
            timeline.setId(null);
            budgetService.saveBudgetTimeline(timeline, this.isRedo());

            if (!notifyMessge) {
                return;
            }
            //发送提醒
            double totalConsumeAmount = bl.getBcAmount() + bl.getNcAmount() + bl.getTrAmount();
            String title = "[" + DateUtil.getFormatDate(bussDay, "yyyy-MM") + "]";
            String content = "月度消费预算" + PriceUtil.changeToString(2, bl.getBudgetAmount()) + "元，";
            content += "截止[" + DateUtil.getFormatDate(bussDay, DateUtil.FormatDay1) + "]已经花费" + PriceUtil.changeToString(2, totalConsumeAmount) + "元，";
            if (totalConsumeAmount < bl.getBudgetAmount()) {
                title += "月度消费预测";
                double am = totalConsumeAmount * tds / pds;
                content += "预计本月总消费" + PriceUtil.changeToString(2, am) + "元。";
            } else {
                title += "月度消费已超预算";
                double am = totalConsumeAmount * tds / pds;
                content += "预计本月总消费" + PriceUtil.changeToString(2, am) + "元。";
            }
            pmsNotifyHandler.addNotifyMessage(PmsErrorCode.BUDGET_CHECK, title, content,
                    bl.getUserId(), null);

        } catch (Exception e) {
            logger.error("处理bussDay=" + bussDay + "的预算异常", e);
        }
    }

    private void handleYear(List<Budget> usList, Date bussDay, int tds, int pds, boolean notifyMessge) {
        try {
            Long userId = usList.get(0).getUserId();
            BudgetAmountBean bab = budgetHandler.calcBudgetAmount(usList, bussDay);
            double budgetAmount = bab.getYearBudget();
            PeriodType period = PeriodType.YEARLY;
            Date[] ds = budgetHandler.getDateRange(period, bussDay, false);
            BudgetLog bl = budgetHandler.statBudget(userId, budgetAmount, ds[0], ds[1], null, false, period);
            //增加时间线流水
            BudgetTimeline timeline = new BudgetTimeline();
            BeanCopy.copyProperties(bl, timeline);
            timeline.setCreatedTime(new Date());
            timeline.setLastModifyTime(null);
            timeline.setBussDay(bussDay);
            timeline.setTotalDays(tds);
            timeline.setPassDays(pds);
            String bussKey = "MS" + budgetHandler.createBussKey(period, bussDay);
            timeline.setBussKey(bussKey);
            timeline.setId(null);
            budgetService.saveBudgetTimeline(timeline, this.isRedo());

            if (!notifyMessge) {
                return;
            }
            double totalConsumeAmount = bl.getBcAmount() + bl.getNcAmount() + bl.getTrAmount();
            String title = "[" + DateUtil.getFormatDate(bussDay, "yyyy") + "]";
            String content = "年度消费预算" + PriceUtil.changeToString(2, bl.getBudgetAmount()) + "元。";
            content += "截止[" + DateUtil.getFormatDate(bussDay, DateUtil.FormatDay1) + "]已经花费" + PriceUtil.changeToString(2, totalConsumeAmount) + "元，";
            if (totalConsumeAmount < bl.getBudgetAmount()) {
                title += "年度消费预测";
                double am = totalConsumeAmount * tds / pds;
                if (am > bl.getBudgetAmount()) {
                    content += "预计今年总消费" + PriceUtil.changeToString(2, am) + "元。";
                }
            } else {
                title += "年度消费已超预算";
                double am = totalConsumeAmount * tds / pds;
                content += "预计今年总消费" + PriceUtil.changeToString(2, am) + "元。";
            }
            pmsNotifyHandler.addNotifyMessage(PmsErrorCode.BUDGET_CHECK, title, content,
                    bl.getUserId(), null);

        } catch (Exception e) {
            logger.error("处理bussDay=" + bussDay + "的预算异常", e);
        }
    }


    @Override
    public ParaCheckResult checkTriggerPara() {
        para = this.getTriggerParaBean();
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return BudgetCheckJobPara.class;
    }
}
