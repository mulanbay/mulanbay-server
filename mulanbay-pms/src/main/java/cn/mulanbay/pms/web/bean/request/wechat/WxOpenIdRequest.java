package cn.mulanbay.pms.web.bean.request.wechat;

import cn.mulanbay.common.aop.BindUser;
import javax.validation.constraints.NotEmpty;

public class WxOpenIdRequest implements BindUser {

    @NotEmpty(message = "{valid.wxPay.code.NotEmpty}")
    private String code;

    private Long userId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
