package cn.mulanbay.pms.web.bean.request.dream;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.schedule.enums.TriggerType;

import javax.validation.constraints.NotNull;

public class DreamRemindFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.dreamRemind.dreamId.NotNull}")
    private Long dreamId;

    //从时间过去的百分比开始，比如月计划，从时间过去50%（即半个月）时开始提醒
    @NotNull(message = "{validate.dreamRemind.formRate.NotNull}")
    private Integer formRate;

    @NotNull(message = "{validate.dreamRemind.fromProposedDays.NotNull}")
    private Integer fromProposedDays;

    //完成时是否要提醒
    @NotNull(message = "{validate.dreamRemind.finishedRemind.NotNull}")
    private Boolean finishedRemind;

    @NotNull(message = "{validate.dreamRemind.triggerType.NotNull}")
    private TriggerType triggerType;

    @NotNull(message = "{validate.dreamRemind.triggerInterval.NotNull}")
    private Integer triggerInterval;

    //提醒时间
    @NotNull(message = "{validate.dreamRemind.remindTime.NotNull}")
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

    public Long getDreamId() {
        return dreamId;
    }

    public void setDreamId(Long dreamId) {
        this.dreamId = dreamId;
    }

    public Integer getFormRate() {
        return formRate;
    }

    public void setFormRate(Integer formRate) {
        this.formRate = formRate;
    }

    public Integer getFromProposedDays() {
        return fromProposedDays;
    }

    public void setFromProposedDays(Integer fromProposedDays) {
        this.fromProposedDays = fromProposedDays;
    }

    public Boolean getFinishedRemind() {
        return finishedRemind;
    }

    public void setFinishedRemind(Boolean finishedRemind) {
        this.finishedRemind = finishedRemind;
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
