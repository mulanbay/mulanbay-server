package cn.mulanbay.pms.web.bean.request.notify;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.schedule.enums.TriggerType;

import javax.validation.constraints.NotNull;

public class UserNotifyRemindFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.userNotifyRemind.userNotifyId.NotNull}")
    private Long userNotifyId;

    @NotNull(message = "{validate.userNotifyRemind.triggerType.NotNull}")
    private TriggerType triggerType;

    @NotNull(message = "{validate.userNotifyRemind.triggerInterval.NotNull}")
    private Integer triggerInterval;

    //超过告警值提醒
    @NotNull(message = "{validate.userNotifyRemind.overWarningRemind.NotNull}")
    private Boolean overWarningRemind;

    //超过警报值提醒
    @NotNull(message = "{validate.userNotifyRemind.overAlertRemind.NotNull}")
    private Boolean overAlertRemind;

    //提醒时间
    @NotNull(message = "{validate.userNotifyRemind.remindTime.NotNull}")
    private String remindTime;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserNotifyId() {
        return userNotifyId;
    }

    public void setUserNotifyId(Long userNotifyId) {
        this.userNotifyId = userNotifyId;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public Integer getTriggerInterval() {
        return triggerInterval;
    }

    public void setTriggerInterval(Integer triggerInterval) {
        this.triggerInterval = triggerInterval;
    }

    public Boolean getOverWarningRemind() {
        return overWarningRemind;
    }

    public void setOverWarningRemind(Boolean overWarningRemind) {
        this.overWarningRemind = overWarningRemind;
    }

    public Boolean getOverAlertRemind() {
        return overAlertRemind;
    }

    public void setOverAlertRemind(Boolean overAlertRemind) {
        this.overAlertRemind = overAlertRemind;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
