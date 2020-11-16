package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;

public class UserBehaviorConfigTreeSearch extends CommonTreeSearch implements BindUser {

    private UserBehaviorType behaviorType;

    private CommonStatus status;

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }
}
