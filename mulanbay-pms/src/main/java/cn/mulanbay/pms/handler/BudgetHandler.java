package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.handler.bean.BudgetAmountBean;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.BudgetLog;
import cn.mulanbay.pms.persistent.dto.BuyRecordConsumeTypeStat;
import cn.mulanbay.pms.persistent.dto.IncomeSummaryStat;
import cn.mulanbay.pms.persistent.dto.TreatRecordSummaryStat;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.service.BudgetService;
import cn.mulanbay.pms.persistent.service.BuyRecordService;
import cn.mulanbay.pms.persistent.service.IncomeService;
import cn.mulanbay.pms.persistent.service.TreatService;
import cn.mulanbay.pms.web.bean.request.health.TreatRecordSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 预算处理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class BudgetHandler extends BaseHandler {

    @Autowired
    BuyRecordService buyRecordService;

    @Autowired
    TreatService treatService;

    @Autowired
    BudgetService budgetService;

    @Autowired
    IncomeService incomeService;

    public BudgetHandler() {
        super("预算处理");
    }

    /**
     * 获取消费记录
     *
     * @param startTime
     * @param endTime
     * @param userId
     * @return
     */
    public double[] getBuyRecordConsume(Date startTime, Date endTime, Long userId) {
        //总消费
        List<BuyRecordConsumeTypeStat> brcList = buyRecordService.getConsumeTypeAmountStat(startTime, endTime, userId);
        //普通消费
        double ncAmount = 0;
        //突发消费
        double bcAmount = 0;
        for (BuyRecordConsumeTypeStat brc : brcList) {
            if (brc.getConsumeType().intValue() == GoodsConsumeType.NORMAL.getValue()) {
                ncAmount = brc.getTotalPrice().doubleValue();
            } else if (brc.getConsumeType().intValue() == GoodsConsumeType.OUTBURST.getValue()) {
                bcAmount = brc.getTotalPrice().doubleValue();
            }
        }

        return new double[]{ncAmount, bcAmount};
    }

    /**
     * 实际支付金额
     * @param budget
     * @return
     */
    public Double getActualAmount(Budget budget,Date bussDay) {
        BudgetFeeType feeType = budget.getFeeType();
        //没有绑定类型
        if(feeType==null){
            return null;
        }
        Double v =null;
        Date[] ds = this.getDateRange(budget.getPeriod(), bussDay, true);
        if(feeType==BudgetFeeType.BUY_RECORD){
            v = buyRecordService.statBuyAmount(ds[0],ds[1],budget.getUserId(),budget.getGoodsTypeId(),budget.getSubGoodsTypeId(),budget.getKeywords());
        }else if(feeType==BudgetFeeType.TREAT_RECORD){
            TreatRecordSummaryStat data = this.statTreatRecord(ds[0],ds[1],budget.getUserId());
            v = data.getTotalPersonalPaidFee();
        }
        return v;
    }

    /**
     * 获取看病消费
     *
     * @param startTime
     * @param endTime
     * @param userId
     * @return
     */
    public double getTreadConsume(Date startTime, Date endTime, Long userId) {
        TreatRecordSummaryStat data = this.statTreatRecord(startTime,endTime,userId);
        double treatAmount = data.getTotalPersonalPaidFee() == null ? 0.0 : data.getTotalPersonalPaidFee();
        return treatAmount;
    }

    private TreatRecordSummaryStat statTreatRecord(Date startTime, Date endTime, Long userId){
        //看病消费
        TreatRecordSearch trs = new TreatRecordSearch();
        trs.setStartDate(startTime);
        trs.setEndDate(endTime);
        trs.setUserId(userId);
        TreatRecordSummaryStat data = treatService.statTreatRecord(trs);
        return data;
    }

    public String createBussKey(PeriodType period, Date date) {
        String dateFormat = DateUtil.Format24Datetime2;
        if (period == PeriodType.YEARLY) {
            dateFormat = "yyyy";
        } else if (period == PeriodType.MONTHLY) {
            dateFormat = "yyyyMM";
        } else if (period == PeriodType.DAILY) {
            dateFormat = "yyyyMMdd";
        } else if (period == PeriodType.WEEKLY) {
            dateFormat = "yyyy";
        } else if (period == PeriodType.QUARTERLY) {
            dateFormat = "yyyyMM";
        }
        return DateUtil.getFormatDate(date, dateFormat);
    }

    /**
     * 统计调度
     *
     * @param userId
     * @param budgetAmount
     * @param startTime
     * @param endTime
     * @param bussKey
     * @param isRedo
     * @param period
     * @return
     */
    public BudgetLog statBudget(Long userId, double budgetAmount, Date startTime, Date endTime, String bussKey, boolean isRedo, PeriodType period) {
        //step 2:查询实际的消费情况
        //Date[] dr = this.getDateRange(period,bussDay);
        double[] dd = this.getBuyRecordConsume(startTime, endTime, userId);
        double treatAmount = this.getTreadConsume(startTime, endTime, userId);
        //step 3:保存记录
        BudgetLog bl = new BudgetLog();
        bl.setBussKey(bussKey);
        if (isRedo) {
            //查找原来的记录
            BudgetLog ori = budgetService.selectBudgetLog(bussKey, userId, null, null);
            if (ori != null) {
                bl.setBudgetAmount(ori.getBudgetAmount());
            } else {
                bl.setBudgetAmount(budgetAmount);
            }
        } else {
            bl.setBudgetAmount(budgetAmount);
        }
        bl.setNcAmount(dd[0]);
        bl.setBcAmount(dd[1]);
        bl.setTrAmount(treatAmount);
        bl.setCreatedTime(new Date());
        bl.setOccurDate(startTime);
        bl.setUserId(userId);
        bl.setPeriod(period);
        bl.setRemark("调度自动生成");
        return bl;
    }

    /**
     * 获取时间区间
     *
     * @param period
     * @param bussDay
     * @param useLastDay endTime是否使用最后一天
     *                   true:月度类型则选择该月的最后一天，年度类型则选择该年的最后一天
     *                   false：endTime等于bussDay的23：59：59
     * @return
     */
    public Date[] getDateRange(PeriodType period, Date bussDay, boolean useLastDay) {
        Date startTime;
        Date endTime;
        if (period == PeriodType.MONTHLY) {
            //月的为当月第一天
            startTime = DateUtil.getFirstDayOfMonth(bussDay);
            if (useLastDay) {
                Date endDate = DateUtil.getLastDayOfMonth(bussDay);
                endTime = DateUtil.getTodayTillMiddleNightDate(endDate);
            } else {
                endTime = DateUtil.getTodayTillMiddleNightDate(bussDay);
            }
        } else {
            //年的为当年第一天
            startTime = DateUtil.getYearFirst(bussDay);
            if (useLastDay) {
                Date endDate = DateUtil.getLastDayOfYear(Integer.valueOf(DateUtil.getYear(bussDay)));
                endTime = DateUtil.getTodayTillMiddleNightDate(endDate);
            } else {
                endTime = DateUtil.getTodayTillMiddleNightDate(bussDay);
            }

        }
        //去掉时分秒
        startTime = DateUtil.getFromMiddleNightDate(startTime);
        return new Date[]{startTime, endTime};
    }

    /**
     * 统计及保存预算日志
     *
     * @param usList
     * @param userId
     * @param bussDay
     * @param bussKey
     * @param isRedo
     * @param period
     * @param useLastDay
     * @return
     */
    public BudgetLog statAndSaveBudgetLog(List<Budget> usList, Long userId, Date bussDay, String bussKey, boolean isRedo, PeriodType period, boolean useLastDay) {
        BudgetAmountBean bab = this.calcBudgetAmount(usList, bussDay);
        double budgetAmount = 0;
        List<Budget> ccList;
        if (period == PeriodType.MONTHLY) {
            budgetAmount = bab.getMonthBudget();
            ccList = bab.getMonthBudgetList();
        } else {
            budgetAmount = bab.getYearBudget();
            ccList = bab.getYearBudgetList();
        }
        Date[] ds = this.getDateRange(period, bussDay, useLastDay);

        BudgetLog bl = this.statBudget(userId, budgetAmount, ds[0], ds[1], bussKey, isRedo, period);
        //自动计算
        bl.setSource(BudgetLogSource.AUTO);
        //计算收入
        IncomeSummaryStat iss = incomeService.incomeSummaryStat(userId, ds[0], ds[1]);
        BigDecimal totalAmount = iss.getTotalAmount();
        bl.setIncomeAmount(totalAmount == null ? 0 : iss.getTotalAmount().doubleValue());
        budgetService.saveStatBudgetLog(ccList, bl, isRedo);
        return bl;
    }

    /**
     * 计算预算
     *
     * @param budgetList
     * @return
     */
    public BudgetAmountBean calcBudgetAmount(List<Budget> budgetList, Date now) {
        BudgetAmountBean bab = new BudgetAmountBean();
        for (Budget b : budgetList) {
            if (b.getStatus() == CommonStatus.DISABLE) {
                continue;
            }
            if (b.getPeriod() == PeriodType.WEEKLY) {
                bab.addYearBudget(b.getAmount() * 52);
                bab.addYearBudget(b);
            } else if (b.getPeriod() == PeriodType.MONTHLY) {
                bab.addYearBudget(b.getAmount() * 12);
                bab.addYearBudget(b);
                bab.addMonthBudget(b.getAmount());
                bab.addMonthBudget(b);
            } else if (b.getPeriod() == PeriodType.QUARTERLY) {
                bab.addYearBudget(b.getAmount() * 4);
                bab.addYearBudget(b);
            } else if (b.getPeriod() == PeriodType.YEARLY) {
                bab.addYearBudget(b.getAmount());
                bab.addYearBudget(b);
                Date date = b.getExpectPaidTime();
                if (date != null) {
                    //月度预算
                    String m1 = DateUtil.getFormatDate(now, "MM");
                    String m2 = DateUtil.getFormatDate(date, "MM");
                    if (m1.equals(m2)) {
                        //同一个月
                        bab.addMonthBudget(b.getAmount());
                        bab.addMonthBudget(b);
                    }
                }
            } else if (b.getPeriod() == PeriodType.ONCE) {
                Date date = b.getExpectPaidTime();
                if (date != null) {
                    //年度预算
                    String y1 = DateUtil.getFormatDate(now, "yyyy");
                    String y2 = DateUtil.getFormatDate(date, "yyyy");
                    if (y1.equals(y2)) {
                        //同一年
                        bab.addYearBudget(b.getAmount());
                        bab.addYearBudget(b);
                    }
                    //月度预算
                    String m1 = DateUtil.getFormatDate(now, "yyyyMM");
                    String m2 = DateUtil.getFormatDate(date, "yyyyMM");
                    if (m1.equals(m2)) {
                        //同一个月
                        bab.addMonthBudget(b.getAmount());
                        bab.addMonthBudget(b);
                    }
                }

            } else if (b.getPeriod() == PeriodType.DAILY) {
                int ydays = DateUtil.getDays(Integer.valueOf(DateUtil.getFormatDate(now, "yyyy")));
                bab.addYearBudget(b.getAmount() * ydays);
                bab.addYearBudget(b);
                int mdays = DateUtil.getMonthDays(now);
                bab.addMonthBudget(b.getAmount() * mdays);
                bab.addMonthBudget(b);
            }
        }
        return bab;
    }

    /**
     * 获取剩余时间
     *
     * @param bg
     * @param now
     * @return
     */
    public Integer getLeftDays(Budget bg, Date now) {
        Date nextPayTime = this.getNextPayTime(bg, now);
        if (nextPayTime == null) {
            return null;
        } else {
            return DateUtil.getIntervalDays(now, nextPayTime);
        }
    }

    /**
     * 获取下一次支付时间
     *
     * @param bg
     * @param now
     * @return
     */
    public Date getNextPayTime(Budget bg, Date now) {
        if (bg.getExpectPaidTime() == null) {
            return null;
        }
        PeriodType period = bg.getPeriod();
        if (period == PeriodType.ONCE) {
            return bg.getExpectPaidTime();
        } else if (period == PeriodType.DAILY) {
            String date = DateUtil.getFormatDate(bg.getExpectPaidTime(), "yyyy-MM-99 HH:mm:ss");
            String dd = DateUtil.getFormatDate(now, "dd");
            String newDate = date.replaceAll("99", dd);
            return DateUtil.getDate(newDate, DateUtil.Format24Datetime);
        } else if (period == PeriodType.MONTHLY) {
            //取几号
            String date = DateUtil.getFormatDate(bg.getExpectPaidTime(), "9999-99-dd HH:mm:ss");
            String dd = DateUtil.getFormatDate(now, "yyyy-MM");
            String newDate = date.replaceAll("9999-99", dd);
            return DateUtil.getDate(newDate, DateUtil.Format24Datetime);
        } else if (period == PeriodType.YEARLY) {
            //取几月几号
            String date = DateUtil.getFormatDate(bg.getExpectPaidTime(), "9999-MM-dd HH:mm:ss");
            String dd = DateUtil.getFormatDate(now, "yyyy");
            String newDate = date.replaceAll("9999", dd);
            return DateUtil.getDate(newDate, DateUtil.Format24Datetime);
        }
        return null;
    }
}
