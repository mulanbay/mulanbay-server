package cn.mulanbay.pms.web.bean.request.user;

import javax.validation.constraints.Min;

public class UserManagerRequest {

    @Min(value = 1)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
