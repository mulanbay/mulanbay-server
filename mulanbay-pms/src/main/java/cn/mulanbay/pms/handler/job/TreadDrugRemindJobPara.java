package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class TreadDrugRemindJobPara extends AbstractTriggerPara {

    @JobParameter(name = "提醒时间", dataType = String.class, desc = "格式为08:30", editType = EditType.TIME)
    private String remindTime = "08:30";

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
}
