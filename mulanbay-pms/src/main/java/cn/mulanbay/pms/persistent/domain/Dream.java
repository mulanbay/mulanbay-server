package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.persistent.enums.DreamStatus;
import cn.mulanbay.pms.util.DreamUtil;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 梦想
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "dream")
public class Dream implements java.io.Serializable {
    private static final long serialVersionUID = 2715806005973261424L;
    private Long id;
    private Long userId;
    private String name;
    private Integer minMoney;
    private Integer maxMoney;
    private Integer difficulty;
    private Double importantLevel;
    private Integer expectDays;
    private DreamStatus status;
    private Integer rate;
    private Date proposedDate;
    private Date deadline;
    private Date finishedDate;
    //延期次数
    private Integer delays;
    //针对proposedDate的改变历史，格式:2017-01-01-->2017-03-02
    private String dateChangeHistory;
    //关联的计划配置项，到时根据无时间条件来计算进度
    private UserPlan userPlan;
    //计划值(小时、天、次数等等)
    private Long planValue;
    private Boolean remind;
    //奖励积分(正为加，负为减)
    private Integer rewardPoint;
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
    @Column(name = "min_money")
    public Integer getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Integer minMoney) {
        this.minMoney = minMoney;
    }

    @Basic
    @Column(name = "max_money")
    public Integer getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Integer maxMoney) {
        this.maxMoney = maxMoney;
    }

    @Basic
    @Column(name = "difficulty")
    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    @Basic
    @Column(name = "important_level")
    public Double getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(Double importantLevel) {
        this.importantLevel = importantLevel;
    }

    @Basic
    @Column(name = "expect_days")
    public Integer getExpectDays() {
        return expectDays;
    }

    public void setExpectDays(Integer expectDays) {
        this.expectDays = expectDays;
    }

    @Basic
    @Column(name = "status")
    public DreamStatus getStatus() {
        return status;
    }

    public void setStatus(DreamStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "rate")
    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "proposed_date")
    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "deadline")
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "finished_date")
    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Basic
    @Column(name = "delays")
    public Integer getDelays() {
        return delays;
    }

    public void setDelays(Integer delays) {
        this.delays = delays;
    }

    @Basic
    @Column(name = "date_change_history")
    public String getDateChangeHistory() {
        return dateChangeHistory;
    }

    public void setDateChangeHistory(String dateChangeHistory) {
        this.dateChangeHistory = dateChangeHistory;
    }

    @ManyToOne
    @JoinColumn(name = "user_plan_id")
    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }

    @Basic
    @Column(name = "plan_value")
    public Long getPlanValue() {
        return planValue;
    }

    public void setPlanValue(Long planValue) {
        this.planValue = planValue;
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
    @Column(name = "reward_point")
    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
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
    public String getStatusName() {
        if (this.status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

    /**
     * 实现日期获取距离现在还剩多少天
     *
     * @return
     */
    @Transient
    public int getLeftDays() {
        if (this.status == null || this.status == DreamStatus.FINISHED || this.status == DreamStatus.GIVEDUP || this.status == DreamStatus.PAUSED) {
            return 0;
        }
        return DateUtil.getIntervalDays(new Date(), this.getProposedDate());
    }

    @Transient
    public int getEmergencyScore() {
        return DreamUtil.getEmergencyScore(this);
    }
}
