package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.persistent.domain.PlanReport;
import cn.mulanbay.pms.persistent.domain.UserCalendar;
import cn.mulanbay.pms.persistent.domain.UserPlan;
import cn.mulanbay.pms.persistent.domain.UserPlanRemind;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.service.PlanService;
import cn.mulanbay.pms.persistent.service.UserCalendarService;
import cn.mulanbay.pms.persistent.service.UserPlanService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.enums.TriggerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 统计用户计划完成进度的调度
 * 如果进度达不到要求（和时间的进度想比），往消息表写一条待发送记录
 * 一般为每天凌晨统计，根据用户配置的提醒时间设置expectSendTime值
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class UserPlanRemindJob extends AbstractBaseRemindJob {

    private static final Logger logger = LoggerFactory.getLogger(UserPlanRemindJob.class);

    UserPlanRemindJobPara para;

    UserPlanService userPlanService = null;

    PlanService planService = null;

    PmsNotifyHandler pmsNotifyHandler = null;

    CacheHandler cacheHandler = null;

    RewardPointsHandler rewardPointsHandler = null;

    UserCalendarService userCalendarService = null;

    BaseService baseService = null;

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        if (para.getPlanType() == null) {
            result.setExecuteResult(JobExecuteResult.SKIP);
            result.setComment("计划类型为空，无法执行调度");
        }
        userPlanService = BeanFactoryUtil.getBean(UserPlanService.class);
        planService = BeanFactoryUtil.getBean(PlanService.class);
        pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
        cacheHandler = BeanFactoryUtil.getBean(CacheHandler.class);
        rewardPointsHandler = BeanFactoryUtil.getBean(RewardPointsHandler.class);
        userCalendarService = BeanFactoryUtil.getBean(UserCalendarService.class);
        baseService = BeanFactoryUtil.getBean(BaseService.class);
        List<UserPlan> list = userPlanService.getNeedRemindUserPlan(para.getPlanType());
        if (list.isEmpty()) {
            result.setComment("没有需要提醒的用户计划");
        } else {
            int success = 0;
            int fail = 0;
            for (UserPlan userPlan : list) {
                boolean b = handleUserPlanRemind(userPlan);
                if (b) {
                    success++;
                } else {
                    fail++;
                }
            }
            result.setComment("一共统计了" + list.size() + "个用户计划,成功:" + success + "个,失败" + fail + "个");
            result.setExecuteResult(JobExecuteResult.SUCCESS);
        }
        return result;
    }

    private boolean handleUserPlanRemind(UserPlan userPlan) {
        try {
            if (!userPlan.getRemind()) {
                return true;
            }
            Long userId = userPlan.getUserId();
            //Step 1:  第一步先判断是否已经通知过
            String key = CacheKey.getKey(CacheKey.USER_PLAN_NOTIFY, userId.toString(), userPlan.getId().toString());
            String cs = cacheHandler.getForString(key);
            if (cs != null) {
                logger.debug("用户ID=" + userId + "的计划[" + userPlan.getTitle() + "],id=" + userPlan.getId() + "已经提醒过了");
                return true;
            }
            CompareType compareType = userPlan.getPlanConfig().getCompareType();
            UserPlanRemind remind = userPlanService.getRemindByUserPlan(userPlan.getId(), userPlan.getUserId());
            if (remind == null) {
                remind = new UserPlanRemind();
                remind.setCreatedTime(new Date());
                remind.setFinishedRemind(true);
                remind.setFormTimePassedRate(50);
                remind.setRemark("由调度系统自动生成");
                remind.setRemindTime("08:30");
                remind.setTriggerInterval(1);
                remind.setTriggerType(TriggerType.DAY);
                remind.setUserId(userId);
                remind.setUserPlan(userPlan);
                baseService.saveObject(remind);
            }
            //Step 2: 通知
            // 通过缓存查询上一次提醒时间
            //需要用运营日计算，比如2017-12-01号调度的，应该是用2017-11-30号计算
            Date date = this.getBussDay();
            int totalDays = this.getTotalDaysPlan(date);
            int dayIndex = this.getDayOfPlan(date);
            //已经过去几天
            double rate = NumberUtil.getPercentValue(dayIndex, totalDays, 0);
            if (rate >= remind.getFormTimePassedRate().doubleValue()) {

                //统计
                PlanReport planReport = planService.statPlanReport(userPlan, date, userId, PlanReportDataStatFilterType.ORIGINAL);
                double planCountRate = NumberUtil.getPercentValue(planReport.getReportCountValue(), planReport.getPlanCountValue(), 2);
                double planValueRate = NumberUtil.getPercentValue(planReport.getReportValue(), planReport.getPlanValue(), 2);
                if (compareType == CompareType.MORE) {
                    //大于类型(即完成的值必须要大于这个)
                    if (planCountRate < rate || planValueRate < rate) {
                        //进度落后
                        //提醒
                        String title = "计划[" + userPlan.getTitle() + "]提醒";
                        StringBuffer content = new StringBuffer();
                        String unit = userPlan.getPlanConfig().getUnit();
                        content.append("你的计划[" + userPlan.getTitle() + "]进度落后了,时间已经过去" + rate + "%,但是进度没有赶上。\n");
                        content.append("计划的次数已经完成[" + planReport.getReportCountValue() + "]次,期望[" + planReport.getPlanCountValue() + "]次,完成进度：" + planCountRate + "%\n");
                        content.append("计划的值已经完成[" + planReport.getReportValue() + "]" + unit + ",期望[" + planReport.getPlanValue() + "]" + unit + ",完成进度：" + planValueRate + "%\n");
                        content.append(getStatDayRangInfo(planReport, date));
                        this.notifyMessage(title, content.toString(), remind, false);
                    } else {
                        //计划完成
                        String title = "计划[" + userPlan.getTitle() + "]完成";
                        StringBuffer content = new StringBuffer();
                        String unit = userPlan.getPlanConfig().getUnit();
                        content.append("你的计划[" + userPlan.getTitle() + "]已经完成\n");
                        content.append("计划次数已经完成[" + planReport.getReportCountValue() + "]次,期望[" + planReport.getPlanCountValue() + "]次,完成进度：" + planCountRate + "%\n");
                        content.append("计划值已经完成[" + planReport.getReportValue() + "]" + unit + ",期望[" + planReport.getPlanValue() + "]" + unit + ",完成进度：" + planValueRate + "%\n");
                        content.append(getStatDayRangInfo(planReport, date));
                        this.notifyMessage(title, content.toString(), remind, true);
                    }
                } else {
                    //小于类型
                    //超标
                    if (planReport.getReportCountValue() > planReport.getPlanCountValue() || planReport.getReportValue() > planReport.getPlanValue()) {
                        String title = "计划[" + userPlan.getTitle() + "]提醒";
                        StringBuffer content = new StringBuffer();
                        String unit = userPlan.getPlanConfig().getUnit();
                        content.append("你的计划[" + userPlan.getTitle() + "]已经超出预期\n");
                        content.append("计划的次数已经达到[" + planReport.getReportCountValue() + "]次,期望[" + planReport.getPlanCountValue() + "]次\n");
                        content.append("计划的值已经达到[" + planReport.getReportValue() + "]" + unit + ",期望[" + planReport.getPlanValue() + "]" + unit + "\n");
                        content.append(getStatDayRangInfo(planReport, date));
                        this.notifyMessage(title, content.toString(), remind, true);
                    } else if (dayIndex == totalDays) {
                        // 完成目标
                        String title = "计划[" + userPlan.getTitle() + "]完成";
                        StringBuffer content = new StringBuffer();
                        String unit = userPlan.getPlanConfig().getUnit();
                        content.append("你的计划[" + userPlan.getTitle() + "]已经满足要求\n");
                        content.append("计划的次数[" + planReport.getReportCountValue() + "]次,期望[" + planReport.getPlanCountValue() + "]次\n");
                        content.append("计划的值[" + planReport.getReportValue() + "]" + unit + ",期望[" + planReport.getPlanValue() + "]" + unit + "\n");
                        content.append(getStatDayRangInfo(planReport, date));
                        this.notifyMessage(title, content.toString(), remind, true);
                    }
                }
                //Step 3: 不能在这里设置上一次的提醒缓存，参见notifyMessage方法
                //Step 4: 加入缓存(方便用户日历统计)
                //cacheHandler.set("userPlanReport:"+userId+":"+DateUtil.getFormatDate(date,DateUtil.FormatDay1),planReport,24*3600);
                return true;
            } else {
                logger.debug("当前时间进度是" + rate + ",配置的最小提醒时间进度为" + remind.getFormTimePassedRate() + ",不提醒");
            }
            return true;
        } catch (Exception e) {
            logger.error("处理用户计划[" + userPlan.getTitle() + "]异常", e);
            return false;
        }
    }

    /**
     * 更新积分，完成+，未达到要求减
     *
     * @param userPlan
     * @param isComplete
     */
    private void rewardPoint(UserPlan userPlan, boolean isComplete, Long messageId) {
        try {
            int radio = 0;
            if (userPlan.getPlanConfig().getPlanType() == PlanType.YEAR) {
                radio = 30;
            } else if (userPlan.getPlanConfig().getPlanType() == PlanType.MONTH) {
                radio = 10;
            }
            int rewards = 0;
            String remark = null;
            if (isComplete) {
                rewards = userPlan.getPlanConfig().getRewardPoint() * radio;
                remark = "计划[" + userPlan.getTitle() + "]完成奖励";
            } else {
                //未完成减去
                rewards = userPlan.getPlanConfig().getRewardPoint() * (-1);
                remark = "计划[" + userPlan.getTitle() + "]进度未达到要求惩罚";
            }
            rewardPointsHandler.rewardPoints(userPlan.getUserId(), rewards, userPlan.getId(), RewardSource.PLAN, remark, messageId);
            if (isComplete) {
                //删除日历
                String bussKey = userPlan.getPlanConfig().getBussKey();
                if (StringUtil.isEmpty(bussKey)) {
                    logger.warn(userPlan.getPlanConfig().getTitle() + "没有配置bussKey");
                    return;
                }
                String bussIdentityKey = bussKey;
                if (!StringUtil.isEmpty(userPlan.getBindValues())) {
                    bussIdentityKey += "_" + userPlan.getBindValues();
                    userCalendarService.updateUserCalendarForFinish(userPlan.getUserId(), bussIdentityKey, new Date(), UserCalendarFinishType.AUTO, messageId);
                }
            } else {
                //添加到用户日历
                addToUserCalendar(userPlan, messageId);
            }
        } catch (Exception e) {
            logger.error("计划[" + userPlan.getTitle() + "]积分奖励异常", e);
        }
    }

    /**
     * 更新到用户日历
     *
     * @param userPlan
     */
    private void addToUserCalendar(UserPlan userPlan, Long messageId) {
        try {
            String bussKey = userPlan.getPlanConfig().getBussKey();
            if (StringUtil.isEmpty(bussKey)) {
                logger.warn(userPlan.getPlanConfig().getTitle() + "没有配置bussKey");
                return;
            }
            String bussIdentityKey = bussKey;
            if (!StringUtil.isEmpty(userPlan.getBindValues())) {
                bussIdentityKey += "_" + userPlan.getBindValues();
            }
            UserCalendar uc = userCalendarService.getUserCalendar(userPlan.getUserId(), bussIdentityKey, new Date());
            if (uc != null) {
                userCalendarService.updateUserCalendarToDate(uc, new Date(), messageId);
            } else {
                uc = new UserCalendar();
                uc.setUserId(userPlan.getUserId());
                uc.setTitle(userPlan.getCalendarTitle());
                uc.setContent(userPlan.getCalendarTitle());
                uc.setDelayCounts(0);
                String calendarTime = userPlan.getCalendarTime();
                if (StringUtil.isEmpty(calendarTime)) {
                    uc.setBussDay(DateUtil.getDate(0));
                    uc.setAllDay(true);
                } else {
                    Date bussDay = DateUtil.addHourMinToDate(null, calendarTime);
                    uc.setBussDay(bussDay);
                    uc.setAllDay(false);
                }
                if (userPlan.getPlanConfig().getPlanType() == PlanType.MONTH) {
                    uc.setExpireTime(DateUtil.getLastDayOfMonth(uc.getBussDay()));
                } else {
                    uc.setExpireTime(DateUtil.getLastDayOfYear(DateUtil.getYear(uc.getBussDay())));
                }
                uc.setBussIdentityKey(bussIdentityKey);
                uc.setSourceType(UserCalendarSource.PLAN);
                uc.setSourceId(userPlan.getId().toString());
                uc.setMessageId(messageId);
                userCalendarService.addUserCalendarToDate(uc);
            }
        } catch (Exception e) {
            logger.error("添加到用户日历异常", e);
        }

    }

    private String getStatDayRangInfo(PlanReport planReport, Date date) {
        String s = "期望数据参考年份:" + planReport.getPlanConfigYear() + "\n";
        Date firstDate = getFirstDate(date);
        s += "统计日期:" + DateUtil.getFormatDate(firstDate, DateUtil.FormatDay1) + "~" + DateUtil.getFormatDate(date, DateUtil.FormatDay1);
        return s;
    }

    private Date getFirstDate(Date date) {
        if (para.getPlanType() == PlanType.YEAR) {
            return DateUtil.getYearFirst(date);
        } else if (para.getPlanType() == PlanType.MONTH) {
            return DateUtil.getFirstDayOfMonth(date);
        } else {
            return date;
        }
    }

    /**
     * 计划所在总的天数
     *
     * @param date
     * @return
     */
    private int getTotalDaysPlan(Date date) {
        if (para.getPlanType() == PlanType.MONTH) {
            return DateUtil.getMonthDays(date);
        } else if (para.getPlanType() == PlanType.YEAR) {
            return DateUtil.getYearDays(date);
        } else {
            return 0;
        }
    }

    /**
     * 计划所在总的天数
     *
     * @param date
     * @return
     */
    private int getDayOfPlan(Date date) {
        if (para.getPlanType() == PlanType.MONTH) {
            return DateUtil.getDayOfMonth(date);
        } else if (para.getPlanType() == PlanType.YEAR) {
            return DateUtil.getDayOfYear(date);
        } else {
            return 0;
        }
    }

    /**
     * 消息提醒
     *
     * @param title
     * @param content
     * @param remind
     * @param isComplete
     */
    private void notifyMessage(String title, String content, UserPlanRemind remind, boolean isComplete) {
        UserPlan userPlan = remind.getUserPlan();
        RemindTimeBean bean = this.calcRemindExpectTime(remind.getTriggerInterval(), remind.getTriggerType(), remind.getLastRemindTime(), remind.getRemindTime());
        //Step 1: 发送消息通知
        Long messageId;
        if (!isComplete) {
            messageId = pmsNotifyHandler.addNotifyMessage(PmsErrorCode.USER_PLAN_UN_COMPLETED_STAT, title, content,
                    userPlan.getUserId(), bean.getNextRemindTime());
        } else {
            messageId = pmsNotifyHandler.addNotifyMessage(PmsErrorCode.USER_PLAN_COMPLETED_STAT, title, content,
                    userPlan.getUserId(), bean.getNextRemindTime());
        }
        //Step 2: 更新最后的提醒时间
        userPlanService.updateLastRemindTime(remind.getId(), new Date());
        Date nextRemindTime = bean.getNextRemindTime();
        if (isComplete) {
            //完成后，设置为最后一天
            PlanType planType = userPlan.getPlanConfig().getPlanType();
            if (planType == PlanType.MONTH) {
                nextRemindTime = DateUtil.getLastDayOfMonth(new Date());
            } else if (planType == PlanType.YEAR) {
                nextRemindTime = DateUtil.getLastDayOfCurrYear();
            }
        }
        /**
         * Step 3: 设置提醒的缓存，避免下一次重复提醒
         * 需要在这里设置上一次的提醒缓存，不能再handleUserPlanRemind方法里设置
         * 因为只有触发了提醒条件才能告知下一次是否能提醒
         */
        String key = CacheKey.getKey(CacheKey.USER_PLAN_NOTIFY, userPlan.getUserId().toString(), userPlan.getId().toString());
        int expireSeconds = 0;
        //失效时间为通知周期的秒数，-5为了保证第二次通知时间点job能执行
        if (isComplete) {
            //如果是完成类型那么，设置为最后一天，保证一个周期内只会提醒一次
            expireSeconds = (int) ((nextRemindTime.getTime() - System.currentTimeMillis()) / 1000);
        } else {
            expireSeconds = bean.getDays() * 24 * 3600;
        }
        cacheHandler.set(key, "123", expireSeconds - 5);
        //Step 4:积分奖励
        rewardPoint(userPlan, isComplete, messageId);
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        ParaCheckResult result = new ParaCheckResult();
        para = this.getTriggerParaBean();
        if (para == null) {
            result.setErrorCode(ScheduleErrorCode.TRIGGER_PARA_NULL);
            result.setMessage("调度参数检查失败，参数为空");
        }
        return result;
    }

    @Override
    public Class getParaDefine() {
        return UserPlanRemindJobPara.class;
    }

}
