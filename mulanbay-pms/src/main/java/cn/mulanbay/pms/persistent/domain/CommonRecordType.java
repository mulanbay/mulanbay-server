package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 通用记录类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "common_record_type")
public class CommonRecordType implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = 8575214853538973501L;
    private Integer id;
    private String name;
    private Long userId;
    private String unit;
    //加入月分析
    private Boolean monthStat;
    private Boolean yearStat;
    // 加入八小时之外统计
    private Boolean overWorkStat;

    private CommonStatus status;
    private Short orderIndex;
    //奖励积分(正为加，负为减)
    private Integer rewardPoint;

    // Constructors

    /**
     * default constructor
     */
    public CommonRecordType() {
    }

    public CommonRecordType(String name, CommonStatus status, Short orderIndex) {
        super();
        this.name = name;
        this.status = status;
        this.orderIndex = orderIndex;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
    @Column(name = "year_stat")
    public Boolean getYearStat() {
        return yearStat;
    }

    public void setYearStat(Boolean yearStat) {
        this.yearStat = yearStat;
    }

    @Basic
    @Column(name = "over_work_stat")
    public Boolean getOverWorkStat() {
        return overWorkStat;
    }

    public void setOverWorkStat(Boolean overWorkStat) {
        this.overWorkStat = overWorkStat;
    }

    @Column(name = "status", nullable = false)
    public CommonStatus getStatus() {
        return this.status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }


    @Column(name = "order_index", nullable = false)
    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Column(name = "reward_point")
    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
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