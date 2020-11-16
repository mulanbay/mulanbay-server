package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.AccountAdjustType;
import cn.mulanbay.pms.persistent.enums.AccountStatus;
import cn.mulanbay.pms.persistent.enums.AccountType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 账户流水记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "account_flow")
public class AccountFlow implements java.io.Serializable {

    private static final long serialVersionUID = 7254329209805899896L;

    private Long id;
    private Long userId;
    private Account account;
    //账户名称
    private String name;
    //账户卡号
    private String cardNo;
    //账户类型
    private AccountType type;
    private Double beforeAmount;
    private Double afterAmount;
    private AccountAdjustType adjustType;
    //业务key，只针对快照方式有效
    private AccountSnapshotInfo snapshotInfo;
    //账户状态
    private AccountStatus status;
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
    @JoinColumn(name = "account_id", nullable = true)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
    @Column(name = "card_no")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Basic
    @Column(name = "type")
    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "before_amount")
    public Double getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(Double beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    @Basic
    @Column(name = "after_amount")
    public Double getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(Double afterAmount) {
        this.afterAmount = afterAmount;
    }

    @Basic
    @Column(name = "adjust_type")
    public AccountAdjustType getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(AccountAdjustType adjustType) {
        this.adjustType = adjustType;
    }

    @ManyToOne
    @JoinColumn(name = "snapshot_id", nullable = true)
    public AccountSnapshotInfo getSnapshotInfo() {
        return snapshotInfo;
    }

    public void setSnapshotInfo(AccountSnapshotInfo snapshotInfo) {
        this.snapshotInfo = snapshotInfo;
    }

    @Basic
    @Column(name = "status")
    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
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
    public String getAdjustTypeName() {
        return adjustType.getName();
    }

    @Transient
    public String getTypeName() {
        return type.getName();
    }

    @Transient
    public String getStatusName() {
        return status.getName();
    }
}
