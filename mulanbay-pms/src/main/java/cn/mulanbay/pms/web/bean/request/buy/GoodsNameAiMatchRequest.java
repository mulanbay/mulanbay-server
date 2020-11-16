package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import javax.validation.constraints.NotEmpty;

public class GoodsNameAiMatchRequest implements BindUser {

    private Long userId;

    @NotEmpty(message = "{validate.buyRecord.goodsName.notEmpty}")
    private String goodsName;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
