package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户提醒
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_notify")
public class UserNotify implements java.io.Serializable {
    private static final long serialVersionUID = 9158813898530320772L;
    private Long id;
    private String title;
    private Long userId;
    private NotifyConfig notifyConfig;
    private String bindValues;
    private Boolean remind;
    private Boolean showInIndex;
    private Integer warningValue;
    private Integer alertValue;
    private CommonStatus status;
    private Short orderIndex;
    private String calendarTitle;
    private String calendarTime;
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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notify_config_id", nullable = false)
    public NotifyConfig getNotifyConfig() {
        return notifyConfig;
    }

    public void setNotifyConfig(NotifyConfig notifyConfig) {
        this.notifyConfig = notifyConfig;
    }

    @Basic
    @Column(name = "bind_values")
    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
    }

    @Column(name = "remind")
    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    @Column(name = "show_in_index")
    public Boolean getShowInIndex() {
        return showInIndex;
    }

    public void setShowInIndex(Boolean showInIndex) {
        this.showInIndex = showInIndex;
    }

    @Basic
    @Column(name = "warning_value")
    public Integer getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(Integer warningValue) {
        this.warningValue = warningValue;
    }

    @Basic
    @Column(name = "alert_value")
    public Integer getAlertValue() {
        return alertValue;
    }

    public void setAlertValue(Integer alertValue) {
        this.alertValue = alertValue;
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

    @Column(name = "calendar_title")
    public String getCalendarTitle() {
        return calendarTitle;
    }

    public void setCalendarTitle(String calendarTitle) {
        this.calendarTitle = calendarTitle;
    }

    @Column(name = "calendar_time")
    public String getCalendarTime() {
        return calendarTime;
    }

    public void setCalendarTime(String calendarTime) {
        this.calendarTime = calendarTime;
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
