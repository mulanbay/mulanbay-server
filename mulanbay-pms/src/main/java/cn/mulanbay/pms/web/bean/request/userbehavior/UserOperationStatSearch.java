package cn.mulanbay.pms.web.bean.request.userbehavior;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;
import cn.mulanbay.web.bean.request.PageSearch;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserOperationStatSearch extends PageSearch implements BindUser {

    @NotNull(message = "{validate.stat.startTime.NotNull}")
    private Date startTime;

    @NotNull(message = "{validate.stat.endTime.NotNull}")
    private Date endTime;

    public Long userId;

    private String configIds;

    private UserBehaviorType behaviorType;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getConfigIds() {
        return configIds;
    }

    public void setConfigIds(String configIds) {
        this.configIds = configIds;
    }

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
    }
}
