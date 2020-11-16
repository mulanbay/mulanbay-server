package cn.mulanbay.schedule.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class AutoRedoJobPara extends AbstractTriggerPara {

    @JobParameter(name = "最早的调度日志",dataType = Integer.class,desc = "天数",editType = EditType.NUMBER)
    private int startDays=7;

    @JobParameter(name = "最近的调度日志",dataType = Integer.class,desc = "天数",editType = EditType.NUMBER)
    private int endDays=1;

    public int getStartDays() {
        return startDays;
    }

    public void setStartDays(int startDays) {
        this.startDays = startDays;
    }

    public int getEndDays() {
        return endDays;
    }

    public void setEndDays(int endDays) {
        this.endDays = endDays;
    }
}
