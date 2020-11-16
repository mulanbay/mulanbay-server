package cn.mulanbay.pms.web.bean.request.userbehavior;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserOperationConfigFormRequest {

    private Long id;

    //名称
    @NotEmpty(message = "{validate.userOperationConfig.name.notEmpty}")
    private String name;

    @NotNull(message = "{validate.userOperationConfig.behaviorType.NotNull}")
    private UserBehaviorType behaviorType;
    //查询语句
    @NotEmpty(message = "{validate.userOperationConfig.sqlContent.notEmpty}")
    private String sqlContent;

    @NotEmpty(message = "{validate.userOperationConfig.columnTemplate.notEmpty}")
    private String columnTemplate;

    @NotNull(message = "{validate.userOperationConfig.orderIndex.NotNull}")
    private Short orderIndex;
    //账户状态
    @NotNull(message = "{validate.userOperationConfig.status.NotNull}")
    private CommonStatus status;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
