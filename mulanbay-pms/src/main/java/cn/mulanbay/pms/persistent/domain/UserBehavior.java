package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户行为
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_behavior")
public class UserBehavior implements java.io.Serializable {
    private static final long serialVersionUID = -3555441253731064004L;
    private Long id;
    private Long userId;
    private String title;
    private UserBehaviorConfig userBehaviorConfig;
    private String bindValues;
    private CommonStatus status;
    //加入月分析
    private Boolean monthStat;
    //加入天分析
    private Boolean dayStat;
    //加入小时分析
    private Boolean hourStat;
    private Short orderIndex;
    private String unit;
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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_behavior_config_id", nullable = false)
    public UserBehaviorConfig getUserBehaviorConfig() {
        return userBehaviorConfig;
    }

    public void setUserBehaviorConfig(UserBehaviorConfig userBehaviorConfig) {
        this.userBehaviorConfig = userBehaviorConfig;
    }

    @Basic
    @Column(name = "bind_values")
    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
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
    @Column(name = "month_stat")
    public Boolean getMonthStat() {
        return monthStat;
    }

    public void setMonthStat(Boolean monthStat) {
        this.monthStat = monthStat;
    }

    @Basic
    @Column(name = "day_stat")
    public Boolean getDayStat() {
        return dayStat;
    }

    public void setDayStat(Boolean dayStat) {
        this.dayStat = dayStat;
    }

    @Basic
    @Column(name = "hour_stat")
    public Boolean getHourStat() {
        return hourStat;
    }

    public void setHourStat(Boolean hourStat) {
        this.hourStat = hourStat;
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

    @Column(name = "order_index", nullable = false)
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    public String getStatusName() {
        if (status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

}
