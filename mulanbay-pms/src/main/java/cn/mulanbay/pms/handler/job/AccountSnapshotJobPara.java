package cn.mulanbay.pms.handler.job;

import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class AccountSnapshotJobPara extends AbstractTriggerPara {

    @JobParameter(name = "调度的周期类型",
            editType = EditType.COMBOBOX,
            editData = "[{\"id\":\"MONTHLY\",\"text\":\"月度快照\"},{\"id\":\"YEARLY\",\"text\":\"年度快照\"}]",
            dataType = String.class)
    private PeriodType period;

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }
}
