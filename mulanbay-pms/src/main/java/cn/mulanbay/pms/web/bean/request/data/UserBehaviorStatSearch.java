package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserBehaviorStatSearch implements BindUser {

    @NotNull(message = "{validate.stat.date.NotNull}")
    private Date date;

    public Long userId;

    private String name;

    private Long userBehaviorId;

    private DateGroupType dateGroupType;

    private UserBehaviorType behaviorType;

    //数据分组
    private DateGroupType dataType;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    public Long getUserBehaviorId() {
        return userBehaviorId;
    }

    public void setUserBehaviorId(Long userBehaviorId) {
        this.userBehaviorId = userBehaviorId;
    }

    public DateGroupType getDataType() {
        return dataType;
    }

    public void setDataType(DateGroupType dataType) {
        this.dataType = dataType;
    }
}
