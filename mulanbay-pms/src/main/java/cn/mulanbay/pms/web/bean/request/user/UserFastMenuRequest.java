package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;

public class UserFastMenuRequest implements BindUser {

    private Long userId;

    private String functionIds;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }
}
