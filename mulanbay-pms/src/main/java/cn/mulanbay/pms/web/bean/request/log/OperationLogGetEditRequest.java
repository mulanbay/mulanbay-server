package cn.mulanbay.pms.web.bean.request.log;

import cn.mulanbay.pms.persistent.enums.LogCompareType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OperationLogGetEditRequest {

    @NotEmpty(message = "{validate.operationLog.compareId.notEmpty}")
    private String id;

    @NotNull(message = "{validate.operationLog.compareType.NotNull}")
    private LogCompareType compareType;

    @NotEmpty(message = "{validate.operationLog.beanName.notEmpty}")
    private String beanName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LogCompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(LogCompareType compareType) {
        this.compareType = compareType;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
