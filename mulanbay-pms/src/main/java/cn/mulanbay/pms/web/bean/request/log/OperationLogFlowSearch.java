package cn.mulanbay.pms.web.bean.request.log;

import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.FunctionType;
import cn.mulanbay.web.bean.request.PageSearch;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class OperationLogFlowSearch extends PageSearch implements FullEndDateTime {

    @Query(fieldName = "occurEndTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occurEndTime", op = Parameter.Operator.LTE)
    private Date endDate;

    @NotEmpty(message = "{validate.operationLog.idValue.notEmpty}")
    @Query(fieldName = "idValue", op = Parameter.Operator.EQ)
    private String idValue;

    /**
     * 直接用sql模式
     */
    @NotEmpty(message = "{validate.operationLog.beanName.notEmpty}")
    @Query(fieldName = "systemFunction.beanName", op = Parameter.Operator.EQ)
    private String beanName;

    @NotNull(message = "{validate.operationLog.functionType.NotNull}")
    @Query(fieldName = "systemFunction.functionType", op = Parameter.Operator.IN)
    private List<FunctionType> functionTypes;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public List<FunctionType> getFunctionTypes() {
        return functionTypes;
    }

    public void setFunctionTypes(List<FunctionType> functionTypes) {
        this.functionTypes = functionTypes;
    }
}
