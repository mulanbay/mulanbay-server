package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;

import java.util.Date;

public class UserBehaviorCalendarStatSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "bussDay", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "bussDay", op = Parameter.Operator.LTE)
    private Date endDate;

    public Long userId;

    private UserBehaviorType behaviorType;

    private String name;

    private Long userBehaviorId;

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

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserBehaviorId() {
        return userBehaviorId;
    }

    public void setUserBehaviorId(Long userBehaviorId) {
        this.userBehaviorId = userBehaviorId;
    }
}
