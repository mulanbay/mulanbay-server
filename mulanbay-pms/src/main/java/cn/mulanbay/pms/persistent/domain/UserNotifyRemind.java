package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.schedule.enums.TriggerType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户提醒配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_notify_remind")
public class UserNotifyRemind implements java.io.Serializable {

    private static final long serialVersionUID = 4317959207470871680L;
    private Long id;

    private Long userId;

    private UserNotify userNotify;

    //最后一次提醒时间
    private Date lastRemindTime;

    private TriggerType triggerType;

    private Integer triggerInterval;

    //超过告警值提醒
    private Boolean overWarningRemind;

    //超过警报值提醒
    private Boolean overAlertRemind;
    //提醒时间
    private String remindTime;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    public UserNotifyRemind() {
    }

    public UserNotifyRemind(Long id, TriggerType triggerType, Integer triggerInterval, String remindTime) {
        this.id = id;
        this.triggerType = triggerType;
        this.triggerInterval = triggerInterval;
        this.remindTime = remindTime;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_notify_id", nullable = false)
    public UserNotify getUserNotify() {
        return userNotify;
    }

    public void setUserNotify(UserNotify userNotify) {
        this.userNotify = userNotify;
    }

    @Column(name = "last_remind_time")
    public Date getLastRemindTime() {
        return lastRemindTime;
    }

    public void setLastRemindTime(Date lastRemindTime) {
        this.lastRemindTime = lastRemindTime;
    }

    @Column(name = "trigger_type")
    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    @Column(name = "trigger_interval")
    public Integer getTriggerInterval() {
        return triggerInterval;
    }

    public void setTriggerInterval(Integer triggerInterval) {
        this.triggerInterval = triggerInterval;
    }

    @Column(name = "over_warning_remind")
    public Boolean getOverWarningRemind() {
        return overWarningRemind;
    }

    public void setOverWarningRemind(Boolean overWarningRemind) {
        this.overWarningRemind = overWarningRemind;
    }

    @Column(name = "over_alert_remind")
    public Boolean getOverAlertRemind() {
        return overAlertRemind;
    }

    public void setOverAlertRemind(Boolean overAlertRemind) {
        this.overAlertRemind = overAlertRemind;
    }

    @Column(name = "remind_time")
    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "created_time", length = 19)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "last_modify_time", length = 19)
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
