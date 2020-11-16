package cn.mulanbay.pms.web.bean.request.userbehavior;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;
import cn.mulanbay.web.bean.request.PageSearch;

public class UserBehaviorSearch extends PageSearch implements BindUser {

    @Query(fieldName = "userBehaviorConfig.behaviorType", op = Parameter.Operator.EQ)
    private UserBehaviorType behaviorType;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private CommonStatus status;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
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
