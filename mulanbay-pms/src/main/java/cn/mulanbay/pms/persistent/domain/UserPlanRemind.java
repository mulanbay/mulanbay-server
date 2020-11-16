package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.schedule.enums.TriggerType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户计划提醒配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_plan_remind")
public class UserPlanRemind implements java.io.Serializable {

    private static final long serialVersionUID = 3974478414998752381L;
    private Long id;

    private Long userId;

    private UserPlan userPlan;

    //从时间过去的百分比开始，比如月计划，从时间过去50%（即半个月）时开始提醒
    private Integer formTimePassedRate;

    //完成时是否要提醒
    private Boolean finishedRemind;

    //最后一次提醒时间
    private Date lastRemindTime;

    private TriggerType triggerType;
    private Integer triggerInterval;

    //提醒时间
    private String remindTime;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    public UserPlanRemind() {
    }

    public UserPlanRemind(Long id, TriggerType triggerType, Integer triggerInterval, String remindTime) {
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
    @JoinColumn(name = "user_plan_id", nullable = false)
    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }

    @Column(name = "form_time_passed_rate")
    public Integer getFormTimePassedRate() {
        return formTimePassedRate;
    }

    public void setFormTimePassedRate(Integer formTimePassedRate) {
        this.formTimePassedRate = formTimePassedRate;
    }

    @Column(name = "finished_remind")
    public Boolean getFinishedRemind() {
        return finishedRemind;
    }

    public void setFinishedRemind(Boolean finishedRemind) {
        this.finishedRemind = finishedRemind;
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
