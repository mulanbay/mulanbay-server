package cn.mulanbay.pms.web.bean.request.notify;

import cn.mulanbay.common.aop.BindUser;

public class UserNotifyStatDeleteRequest implements BindUser {

    private Long userNotifyId;

    private Long userId;

    public Long getUserNotifyId() {
        return userNotifyId;
    }

    public void setUserNotifyId(Long userNotifyId) {
        this.userNotifyId = userNotifyId;
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
