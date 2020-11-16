package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.SqlType;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户行为配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_behavior_config")
public class UserBehaviorConfig implements java.io.Serializable {
    private static final long serialVersionUID = -5625649846678266289L;
    private Long id;
    private String name;
    private String title;
    private UserBehaviorType behaviorType;
    private SqlType sqlType;
    private String sqlContent;
    //时间字段，时间过滤使用
    private String dateField;
    //用户绑定使用
    private String userField;
    //关键字，可以做基于名称的搜索，跨多个域以英文逗号分隔
    private String keywords;
    //关键字查询语句
    private String keywordsSearchSql;
    //日期区段，比如旅行虽然只有开始日期，其实是跨好几天时间，根据统计出的天数加时间
    private Boolean dateRegion;
    private String unit;
    private CommonStatus status;
    //加入月分析
    private Boolean monthStat;
    // 级别，不同的等级可以看到不同的东西
    private Integer level;
    private Short orderIndex;
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
    @Column(name = "behavior_type")
    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
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
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Basic
    @Column(name = "keywords_search_sql")
    public String getKeywordsSearchSql() {
        return keywordsSearchSql;
    }

    public void setKeywordsSearchSql(String keywordsSearchSql) {
        this.keywordsSearchSql = keywordsSearchSql;
    }

    @Basic
    @Column(name = "date_region")
    public Boolean getDateRegion() {
        return dateRegion;
    }

    public void setDateRegion(Boolean dateRegion) {
        this.dateRegion = dateRegion;
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
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
    public String getBehaviorTypeName() {
        if (behaviorType != null) {
            return behaviorType.getName();
        } else {
            return null;
        }
    }


}
