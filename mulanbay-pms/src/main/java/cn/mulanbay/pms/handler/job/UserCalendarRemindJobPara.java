package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class UserCalendarRemindJobPara extends AbstractTriggerPara {

    @JobParameter(name = "每日任务数保留条数", dataType = Integer.class, desc = "条", editType = EditType.NUMBER)
    private int queueSize = 100;

    /**
     * 实际上这个参数是和该调度的周期相一致的
     */
    @JobParameter(name = "每日任务数保留天数", dataType = Integer.class, desc = "天", editType = EditType.NUMBER)
    private int days = 90;

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
