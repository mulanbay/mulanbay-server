package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class BudgetLogFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.budgetLog.budgetId.notNull}")
    private Long budgetId;

    //金额
    @NotNull(message = "{validate.budgetLog.amount.notNull}")
    private Double amount;

    @NotNull(message = "{validate.budgetLog.occurDate.notNull}")
    private Date occurDate;

    private Long buyRecordId;

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

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Long getBuyRecordId() {
        return buyRecordId;
    }

    public void setBuyRecordId(Long buyRecordId) {
        this.buyRecordId = buyRecordId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
