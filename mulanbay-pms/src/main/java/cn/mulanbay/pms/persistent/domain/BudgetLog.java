package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.BudgetLogSource;
import cn.mulanbay.pms.persistent.enums.PeriodType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 预算日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "budget_log")
public class BudgetLog implements java.io.Serializable {

    private static final long serialVersionUID = 6891444266523508770L;

    private Long id;
    private Long userId;
    private Budget budget;
    //周期类型
    private PeriodType period;
    //业务key，唯一性判断使用，避免重复设置
    private String bussKey;
    //发生的时间
    private Date occurDate;
    //预算金额
    private Double budgetAmount;
    //实际普通消费金额
    private Double ncAmount;
    //实际突发消费金额
    private Double bcAmount;
    //实际看病消费金额
    private Double trAmount;
    //收入
    private Double incomeAmount;
    //账户变化值
    private Double accountChangeAmount;
    private BudgetLogSource source;
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

    @ManyToOne
    @JoinColumn(name = "budget_id")
    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
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
    @Column(name = "buss_key")
    public String getBussKey() {
        return bussKey;
    }

    public void setBussKey(String bussKey) {
        this.bussKey = bussKey;
    }

    @Basic
    @Column(name = "occur_date")
    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    @Basic
    @Column(name = "budget_amount")
    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    @Basic
    @Column(name = "nc_amount")
    public Double getNcAmount() {
        return ncAmount;
    }

    public void setNcAmount(Double ncAmount) {
        this.ncAmount = ncAmount;
    }

    @Basic
    @Column(name = "bc_amount")
    public Double getBcAmount() {
        return bcAmount;
    }

    public void setBcAmount(Double bcAmount) {
        this.bcAmount = bcAmount;
    }

    @Basic
    @Column(name = "tr_amount")
    public Double getTrAmount() {
        return trAmount;
    }

    public void setTrAmount(Double trAmount) {
        this.trAmount = trAmount;
    }

    @Basic
    @Column(name = "income_amount")
    public Double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(Double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    @Basic
    @Column(name = "account_change_amount")
    public Double getAccountChangeAmount() {
        return accountChangeAmount;
    }

    public void setAccountChangeAmount(Double accountChangeAmount) {
        this.accountChangeAmount = accountChangeAmount;
    }

    @Basic
    @Column(name = "source")
    public BudgetLogSource getSource() {
        return source;
    }

    public void setSource(BudgetLogSource source) {
        this.source = source;
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
    public String getPeriodName() {
        return period.getName();
    }

    @Transient
    public String getSourceName() {
        return source ==null ? null : source.getName();
    }
}
