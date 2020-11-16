package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class TreadOperationRemindJobPara extends AbstractTriggerPara {

    @JobParameter(name = "提醒时间", dataType = String.class, desc = "格式为08:30", editType = EditType.TIME)
    private String remindTime = "08:30";

    @JobParameter(name = "检查天数", dataType = Integer.class, desc = "手术复查前多少天提醒", editType = EditType.NUMBER)
    private int checkDays = 7;

    @JobParameter(name = "验证天数", dataType = Integer.class, desc = "验证复查的手术是在几天之内", editType = EditType.NUMBER)
    private int verifyDays = 7;

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public int getCheckDays() {
        return checkDays;
    }

    public void setCheckDays(int checkDays) {
        this.checkDays = checkDays;
    }

    public int getVerifyDays() {
        return verifyDays;
    }

    public void setVerifyDays(int verifyDays) {
        this.verifyDays = verifyDays;
    }
}
