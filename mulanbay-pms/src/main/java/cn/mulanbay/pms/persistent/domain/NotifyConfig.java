package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.*;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户提醒配置模板
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "notify_config")
public class NotifyConfig implements java.io.Serializable {

    private static final long serialVersionUID = 5577140811957103873L;

    private Long id;
    private String name;
    private String title;
    private SqlType sqlType;
    private String sqlContent;
    private ResultType resultType;
    private ValueType valueType;
    private CommonStatus status;
    private Short orderIndex;
    private NotifyType notifyType;
    private String userField;
    //关联的bean，可以根据这个在各个其他界面来查询本业务管理的计划
    private String relatedBeans;
    private Integer level;
    //奖励积分(正为加，负为减)
    private Integer rewardPoint;
    private String bussKey;
    private String defaultCalendarTitle;
    //链接地址
    private String url;
    //tab名称
    private String tabName;
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
    @Column(name = "result_type")
    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    @Basic
    @Column(name = "value_type")
    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
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

    @Column(name = "notify_type", nullable = false)
    public NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
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

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    @Column(name = "related_beans")
    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
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
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "tab_name")
    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
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
    public String getValueTypeName() {
        if (valueType != null) {
            return valueType.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getResultTypeName() {
        if (this.resultType != null) {
            return resultType.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getNotifyTypeName() {
        if (this.notifyType != null) {
            return notifyType.getName();
        } else {
            return null;
        }
    }

}
