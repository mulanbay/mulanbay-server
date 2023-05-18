package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class BuyRecordChildrenTotalCostRequest implements BindUser {

    @NotNull(message = "{validate.buyRecord.id.notNull}")
    private Long id;

    @NotNull(message = "{validate.buyRecord.deepCost.notNull}")
    private Boolean deepCost;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getDeepCost() {
        return deepCost;
    }

    public void setDeepCost(Boolean deepCost) {
        this.deepCost = deepCost;
    }
}
