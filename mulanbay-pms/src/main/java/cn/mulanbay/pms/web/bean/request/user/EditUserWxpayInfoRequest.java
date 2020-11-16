package cn.mulanbay.pms.web.bean.request.user;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class EditUserWxpayInfoRequest {

    private Long id;

    @NotNull(message = "{validate.user.userId.NotNull}")
    private Long userId;

    @NotEmpty(message = "{validate.user.openId.notEmpty}")
    private String openId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
