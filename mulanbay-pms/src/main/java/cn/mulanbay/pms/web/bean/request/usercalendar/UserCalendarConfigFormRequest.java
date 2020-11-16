package cn.mulanbay.pms.web.bean.request.usercalendar;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.SqlType;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserCalendarConfigFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.userCalendarConfig.name.NotEmpty}")
    private String name;

    @NotEmpty(message = "{validate.userCalendarConfig.title.NotEmpty}")
    private String title;

    @NotNull(message = "{validate.userCalendarConfig.sqlType.NotNull}")
    private SqlType sqlType;

    @NotEmpty(message = "{validate.userCalendarConfig.sqlContent.NotEmpty}")
    private String sqlContent;

    @NotNull(message = "{validate.userCalendarConfig.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.userCalendarConfig.orderIndex.NotNull}")
    private Short orderIndex;

    private String userField;

    @NotNull(message = "{validate.userCalendarConfig.level.NotNull}")
    private Integer level;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
