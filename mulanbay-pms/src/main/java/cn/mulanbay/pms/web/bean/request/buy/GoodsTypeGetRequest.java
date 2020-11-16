package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class GoodsTypeGetRequest implements BindUser {

    @NotNull(message = "{validate.goodsType.id.notNull}")
    private Integer id;

    private Long userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
