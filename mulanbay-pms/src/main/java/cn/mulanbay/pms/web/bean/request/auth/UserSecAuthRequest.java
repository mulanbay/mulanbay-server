package cn.mulanbay.pms.web.bean.request.auth;

import cn.mulanbay.common.aop.BindUser;
import javax.validation.constraints.NotEmpty;

public class UserSecAuthRequest implements BindUser {

    private Long userId;

    @NotEmpty(message = "{validate.user.authCode.NotEmpty}")
    private String authCode;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
