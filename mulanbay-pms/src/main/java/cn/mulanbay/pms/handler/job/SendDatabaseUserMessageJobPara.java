package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class SendDatabaseUserMessageJobPara extends AbstractTriggerPara {

    @JobParameter(name = "每次取的最大条数", dataType = Integer.class, desc = "条", editType = EditType.NUMBER)
    private int maxRows = 1000;

    @JobParameter(name = "预期发送时间的最大延迟分钟数", dataType = Integer.class, desc = "分钟", editType = EditType.NUMBER)
    private int compareMinutes = 0;

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getCompareMinutes() {
        return compareMinutes;
    }

    public void setCompareMinutes(int compareMinutes) {
        this.compareMinutes = compareMinutes;
    }
}
