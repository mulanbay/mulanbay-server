package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;

public class UserPasswordEditRequest implements BindUser {

    private Long userId;

    private String oldPassword;

    private String newPassword;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
