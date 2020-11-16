package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.CompareType;
import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.pms.persistent.enums.SqlType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户计划配置模板
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "plan_config")
public class PlanConfig implements java.io.Serializable {
    private static final long serialVersionUID = 3222686239313264692L;
    private Long id;
    private Long userId;
    private String name;
    private String title;
    private PlanType planType;
    private SqlType sqlType;
    private String sqlContent;
    //时间字段，时间过滤使用
    private String dateField;
    //用户绑定使用
    private String userField;
    /**
     * 计划值的单位，这里的值只是在配置用户计划时默认加载使用，实际还是根据UserPlan取里面的unit来
     * 因为比如运动锻炼，跑步的数值单位是公里，而平板支撑的数值单位是分钟
     */
    private String unit;
    private CompareType compareType;
    private CommonStatus status;
    private Short orderIndex;
    //关联的bean，可以根据这个在各个其他界面来查询本业务管理的计划
    private String relatedBeans;
    //次数值
    private Long defaultPlanCountValue;
    //值，比如购买金额，锻炼时间
    private Long defaultPlanValue;
    //等级
    private Integer level;
    //奖励积分(正为加，负为减)
    private Integer rewardPoint;
    private String bussKey;
    private String defaultCalendarTitle;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "plan_type")
    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    @Basic
    @Column(name = "sql_type")
    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    @Basic
    @Column(name = "sql_content")
    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    @Basic
    @Column(name = "date_field")
    public String getDateField() {
        return dateField;
    }

    public void setDateField(String dateField) {
        this.dateField = dateField;
    }

    @Basic
    @Column(name = "user_field")
    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    @Basic
    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "compare_type")
    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
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

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "related_beans")
    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }

    @Basic
    @Column(name = "default_plan_count_value")
    public Long getDefaultPlanCountValue() {
        return defaultPlanCountValue;
    }

    public void setDefaultPlanCountValue(Long defaultPlanCountValue) {
        this.defaultPlanCountValue = defaultPlanCountValue;
    }

    @Basic
    @Column(name = "default_plan_value")
    public Long getDefaultPlanValue() {
        return defaultPlanValue;
    }

    public void setDefaultPlanValue(Long defaultPlanValue) {
        this.defaultPlanValue = defaultPlanValue;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
    @Column(name = "buss_key")
    public String getBussKey() {
        return bussKey;
    }

    public void setBussKey(String bussKey) {
        this.bussKey = bussKey;
    }

    @Basic
    @Column(name = "default_calendar_title")
    public String getDefaultCalendarTitle() {
        return defaultCalendarTitle;
    }

    public void setDefaultCalendarTitle(String defaultCalendarTitle) {
        this.defaultCalendarTitle = defaultCalendarTitle;
    }

    @Transient
    public String getStatusName() {
        if (status != null) {
            return status.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getSqlTypeName() {
        if (this.sqlType != null) {
            return sqlType.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getPlanTypeName() {
        if (planType != null) {
            return planType.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getCompareTypeName() {
        if (compareType != null) {
            return compareType.getName();
        } else {
            return null;
        }
    }


}
