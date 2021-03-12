package cn.mulanbay.pms.web.bean.request.notify;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.BindUserLevel;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserNotifyFormRequest implements BindUser, BindUserLevel {

    private Long id;

    @NotEmpty(message = "{validate.userNotify.title.notEmpty}")
    private String title;
    private Long userId;

    @NotNull(message = "{validate.userNotify.notifyConfigId.NotNull}")
    private Long notifyConfigId;
    private String bindValues;

    @NotNull(message = "{validate.userNotify.remind.NotNull}")
    private Boolean remind;

    @NotNull(message = "{validate.userNotify.showInIndex.NotNull}")
    private Boolean showInIndex;

    @NotNull(message = "{validate.userNotify.warningValue.NotNull}")
    private Integer warningValue;

    @NotNull(message = "{validate.userNotify.alertValue.NotNull}")
    private Integer alertValue;

    @NotNull(message = "{validate.userNotify.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.userNotify.orderIndex.NotNull}")
    private Short orderIndex;

    private String calendarTitle;

    private String calendarTime;

    private String unit;

    private String remark;

    //绑定的用户等级
    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNotifyConfigId() {
        return notifyConfigId;
    }

    public void setNotifyConfigId(Long notifyConfigId) {
        this.notifyConfigId = notifyConfigId;
    }

    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public Boolean getShowInIndex() {
        return showInIndex;
    }

    public void setShowInIndex(Boolean showInIndex) {
        this.showInIndex = showInIndex;
    }

    public Integer getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(Integer warningValue) {
        this.warningValue = warningValue;
    }

    public Integer getAlertValue() {
        return alertValue;
    }

    public void setAlertValue(Integer alertValue) {
        this.alertValue = alertValue;
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

    public String getCalendarTitle() {
        return calendarTitle;
    }

    public void setCalendarTitle(String calendarTitle) {
        this.calendarTitle = calendarTitle;
    }

    public String getCalendarTime() {
        return calendarTime;
    }

    public void setCalendarTime(String calendarTime) {
        this.calendarTime = calendarTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }
}
