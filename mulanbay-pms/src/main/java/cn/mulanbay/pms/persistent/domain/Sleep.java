package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 睡眠记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "sleep")
public class Sleep implements java.io.Serializable {

    private static final long serialVersionUID = 678113094988722465L;

    private Long id;

    private Long userId;

    //睡眠日
    private Date sleepDate;

    //睡觉时间
    private Date sleepTime;

    //起床时间
    private Date getUpTime;

    //醒来时间
    private Date wakeUpTime;

    //醒来次数
    private Integer wakeUpCount;

    private Integer totalMinutes;

    //浅睡时长（分钟）
    private Integer lightSleep;

    //深睡时长（分钟）
    private Integer deepSleep;

    //睡眠质量（0-10分）
    private Integer quality;

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
    @Temporal(TemporalType.DATE)
    @Column(name = "sleep_date", length = 10)
    public Date getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(Date sleepDate) {
        this.sleepDate = sleepDate;
    }

    @Basic
    @Column(name = "sleep_time")
    public Date getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Date sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Basic
    @Column(name = "get_up_time")
    public Date getGetUpTime() {
        return getUpTime;
    }

    public void setGetUpTime(Date getUpTime) {
        this.getUpTime = getUpTime;
    }

    @Basic
    @Column(name = "wake_up_time")
    public Date getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(Date wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    @Basic
    @Column(name = "wake_up_count")
    public Integer getWakeUpCount() {
        return wakeUpCount;
    }

    public void setWakeUpCount(Integer wakeUpCount) {
        this.wakeUpCount = wakeUpCount;
    }

    @Basic
    @Column(name = "total_minutes")
    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    @Basic
    @Column(name = "light_sleep")
    public Integer getLightSleep() {
        return lightSleep;
    }

    public void setLightSleep(Integer lightSleep) {
        this.lightSleep = lightSleep;
    }

    @Basic
    @Column(name = "deep_sleep")
    public Integer getDeepSleep() {
        return deepSleep;
    }

    public void setDeepSleep(Integer deepSleep) {
        this.deepSleep = deepSleep;
    }

    @Basic
    @Column(name = "quality")
    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
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
}
