package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.BudgetType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.PeriodType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class BudgetFormRequest implements BindUser {

    private Long id;

    @NotEmpty(message = "{validate.budget.name.notEmpty}")
    private String name;

    private Long userId;

    //类型
    @NotNull(message = "{validate.budget.type.notNull}")
    private BudgetType type;

    //周期类型
    @NotNull(message = "{validate.budget.period.notNull}")
    private PeriodType period;

    //金额
    @NotNull(message = "{validate.budget.amount.notNull}")
    private Double amount;

    //期望支付时间
    private Date expectPaidTime;

    private String keywords;

    //消费大类（feeType为BUY_RECORD有效）
    private Integer goodsTypeId;
    //消费大类（feeType为BUY_RECORD有效）
    private Integer subGoodsTypeId;

    private String remark;

    @NotNull(message = "{validate.budget.remind.notNull}")
    private Boolean remind;

    @NotNull(message = "{validate.budget.status.notNull}")
    private CommonStatus status;

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

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public BudgetType getType() {
        return type;
    }

    public void setType(BudgetType type) {
        this.type = type;
    }

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getExpectPaidTime() {
        return expectPaidTime;
    }

    public void setExpectPaidTime(Date expectPaidTime) {
        this.expectPaidTime = expectPaidTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Integer getSubGoodsTypeId() {
        return subGoodsTypeId;
    }

    public void setSubGoodsTypeId(Integer subGoodsTypeId) {
        this.subGoodsTypeId = subGoodsTypeId;
    }
}
