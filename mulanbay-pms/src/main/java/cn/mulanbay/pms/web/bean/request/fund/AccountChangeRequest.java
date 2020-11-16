package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class AccountChangeRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.account.amount.notNull}")
    private Double afterAmount;

    private String remark;

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

    public Double getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(Double afterAmount) {
        this.afterAmount = afterAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
