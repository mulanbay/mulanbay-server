package cn.mulanbay.pms.handler.job;

import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class UserPlanRemindJobPara extends AbstractTriggerPara {

    @JobParameter(name = "调度的周期类型",
            editType = EditType.COMBOBOX,
            editData = "[{\"id\":\"YEAR\",\"text\":\"年\"},{\"id\":\"MONTH\",\"text\":\"月\"}]",
            dataType = String.class)
    private PlanType planType;

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }
}
