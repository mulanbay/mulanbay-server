package cn.mulanbay.pms.web.bean.request.schedule;

import cn.mulanbay.common.aop.BindUser;

public class RefreshScheduleRequest implements BindUser {

    private Long taskTriggerId;

    private Long userId;

    private Boolean force;

    public Long getTaskTriggerId() {
        return taskTriggerId;
    }

    public void setTaskTriggerId(Long taskTriggerId) {
        this.taskTriggerId = taskTriggerId;
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
