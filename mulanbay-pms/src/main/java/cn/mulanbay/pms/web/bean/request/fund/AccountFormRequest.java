package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.AccountStatus;
import cn.mulanbay.pms.persistent.enums.AccountType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountFormRequest implements BindUser {

    private Long id;

    @NotEmpty(message = "{validate.account.name.notEmpty}")
    private String name;

    private Long userId;

    private String cardNo;

    @NotNull(message = "{validate.account.type.notNull}")
    private AccountType type;

    @NotNull(message = "{validate.account.amount.notNull}")
    private Double amount;

    private String remark;

    @NotNull(message = "{validate.account.status.notNull}")
    private AccountStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
