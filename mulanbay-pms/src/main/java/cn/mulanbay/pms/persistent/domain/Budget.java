package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.BudgetFeeType;
import cn.mulanbay.pms.persistent.enums.BudgetType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.PeriodType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户预算
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "budget")
public class Budget implements java.io.Serializable {

    private static final long serialVersionUID = 7254329209805899896L;

    private Long id;
    private Long userId;
    //名称
    private String name;
    //类型
    private BudgetType type;
    //周期类型
    private PeriodType period;
    //期望支付时间
    private Date expectPaidTime;
    //第一次支付时间
    private Date firstPaidTime;
    //上一次支付时间
    private Date lastPaidTime;
    //金额
    private Double amount;
    private Boolean remind;
    //账户状态
    private CommonStatus status;
    private String keywords;
    //资金类型
    private BudgetFeeType feeType;
    //消费大类（feeType为BUY_RECORD有效）
    private Integer goodsTypeId;
    //消费大类（feeType为BUY_RECORD有效）
    private Integer subGoodsTypeId;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type")
    public BudgetType getType() {
        return type;
    }

    public void setType(BudgetType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "period")
    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    @Basic
    @Column(name = "expect_paid_time")
    public Date getExpectPaidTime() {
        return expectPaidTime;
    }

    public void setExpectPaidTime(Date expectPaidTime) {
        this.expectPaidTime = expectPaidTime;
    }

    @Basic
    @Column(name = "first_paid_time")
    public Date getFirstPaidTime() {
        return firstPaidTime;
    }

    public void setFirstPaidTime(Date firstPaidTime) {
        this.firstPaidTime = firstPaidTime;
    }

    @Basic
    @Column(name = "last_paid_time")
    public Date getLastPaidTime() {
        return lastPaidTime;
    }

    public void setLastPaidTime(Date lastPaidTime) {
        this.lastPaidTime = lastPaidTime;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "remind")
    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    @Basic
    @Column(name = "status")
    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Basic
    @Column(name = "fee_type")
    public BudgetFeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(BudgetFeeType feeType) {
        this.feeType = feeType;
    }

    @Basic
    @Column(name = "goods_type_id")
    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    @Basic
    @Column(name = "sub_goods_type_id")
    public Integer getSubGoodsTypeId() {
        return subGoodsTypeId;
    }

    public void setSubGoodsTypeId(Integer subGoodsTypeId) {
        this.subGoodsTypeId = subGoodsTypeId;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Transient
    public String getTypeName() {
        return type.getName();
    }

    @Transient
    public String getPeriodName() {
        return period.getName();
    }

    @Transient
    public String getStatusName() {
        return status.getName();
    }
}
