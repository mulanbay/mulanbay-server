package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.schedule.enums.TriggerType;

import javax.validation.constraints.NotNull;

public class UserPlanRemindFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.userPlanRemind.userPlanId.NotNull}")
    private Long userPlanId;

    //从时间过去的百分比开始，比如月计划，从时间过去50%（即半个月）时开始提醒
    @NotNull(message = "{validate.userPlanRemind.formTimePassedRate.NotNull}")
    private Integer formTimePassedRate;

    //完成时是否要提醒
    @NotNull(message = "{validate.userPlanRemind.finishedRemind.NotNull}")
    private Boolean finishedRemind;

    @NotNull(message = "{validate.userPlanRemind.triggerType.NotNull}")
    private TriggerType triggerType;

    @NotNull(message = "{validate.userPlanRemind.triggerInterval.NotNull}")
    private Integer triggerInterval;

    //提醒时间
    @NotNull(message = "{validate.userPlanRemind.remindTime.NotNull}")
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

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public Integer getFormTimePassedRate() {
        return formTimePassedRate;
    }

    public void setFormTimePassedRate(Integer formTimePassedRate) {
        this.formTimePassedRate = formTimePassedRate;
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
