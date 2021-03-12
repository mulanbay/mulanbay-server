package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotEmpty;

public class BuyTypeDeleteRequest implements BindUser {

    @NotEmpty(message = "{validate.buyType.ids.notNull}")
    private String ids;

    private Long userId;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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
