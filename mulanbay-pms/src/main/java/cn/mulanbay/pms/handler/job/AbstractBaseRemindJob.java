package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.schedule.enums.TriggerType;
import cn.mulanbay.schedule.job.AbstractBaseJob;

import java.util.Date;

/**
 * 基础提醒类
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public abstract class AbstractBaseRemindJob extends AbstractBaseJob {

    /**
     * 获取周期的天数
     *
     * @param triggerType
     * @param interval
     * @return
     */
    protected int getIntervalDays(TriggerType triggerType, int interval) {
        int days = 0;
        if (triggerType == TriggerType.DAY) {
            days = interval;
        } else if (triggerType == TriggerType.WEEK) {
            days = 7 * interval;
        } else {
            //默认月
            days = 30 * interval;
        }
        return days;
    }

    /**
     * 计算下一次需要提醒的时间
     * 由上次的提醒时间来判断下一次的提醒时间
     *
     * @param interval
     * @param triggerType
     * @param lastRemindTime
     * @param remindTime
     * @return
     */
    protected RemindTimeBean calcRemindExpectTime(int interval, TriggerType triggerType, Date lastRemindTime, String remindTime) {
        int days = this.getIntervalDays(triggerType, interval);
        RemindTimeBean bean = new RemindTimeBean();
        if (lastRemindTime == null) {
            //说明没有通知过，马上通知
            //算上用户设置的时间:比如现在2017-11-03 03:00:00,用户设置为08：30，那么提醒时间=2017-11-03 08:30:00
            Date next = DateUtil.addHourMinToDate(null, remindTime);
            bean.setNextRemindTime(next);
        } else {
            Date fromLastTime = new Date(lastRemindTime.getTime() + days * 24 * 3600 * 1000);
            String ss = DateUtil.getFormatDate(fromLastTime, DateUtil.FormatDay1);
            //算上用户设置的时间:比如上次提醒2017-11-03 03:00:00,用户设置为08：30，设置为3天提醒一次，那么提醒时间=2017-11-06 08:30:00
            Date next = DateUtil.getDate(ss + " " + remindTime + ":00", DateUtil.Format24Datetime);
            bean.setNextRemindTime(next);
        }
        bean.setDays(days);
        return bean;
    }


    public class RemindTimeBean {
        private int days;

        private Date nextRemindTime;

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public Date getNextRemindTime() {
            return nextRemindTime;
        }

        public void setNextRemindTime(Date nextRemindTime) {
            this.nextRemindTime = nextRemindTime;
        }
    }
}
