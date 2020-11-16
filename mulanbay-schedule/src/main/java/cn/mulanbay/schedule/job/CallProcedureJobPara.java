package cn.mulanbay.schedule.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class CallProcedureJobPara  extends AbstractTriggerPara {

    // 存储过程名称
    @JobParameter(name = "存储过程名称",dataType = String.class,editType = EditType.TEXT)
    private String procedureName;

    @JobParameter(name = "时间参数类型",dataType = Integer.class,desc = "取值范围:0,1,2,3",editType = EditType.NUMBER)
    private int dateParaType;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public int getDateParaType() {
        return dateParaType;
    }

    public void setDateParaType(int dateParaType) {
        this.dateParaType = dateParaType;
    }
}
