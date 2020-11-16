package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.enums.UserCalendarFinishType;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户日历
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_calendar")
public class UserCalendar implements java.io.Serializable {

    private static final long serialVersionUID = 8213897467891411864L;
    private Long id;

    private Long userId;

    private String title;

    private String content;

    //延迟次数
    private Integer delayCounts;

    private Date bussDay;

    //失效时间
    private Date expireTime;

    //唯一key
    private String bussIdentityKey;

    private UserCalendarSource sourceType;

    private String sourceId;

    private Date finishedTime;

    private UserCalendarFinishType finishType;

    private Long messageId;

    //完成的源ID
    private Long finishSourceId;

    //是否为全天任务
    private Boolean allDay;

    //手动设置有用
    private String location;

    private Boolean readOnly;

    private PeriodType period;

    /**
     * 针对重复循环的有效
     */
    private String periodValues;

    //以模板新增的，可以查询是否完成的判断
    private Long calendarConfigId;

    //用户自己选择的值，以模板新增的，可以查询是否完成的判断
    private String bindValues;

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

    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "delay_counts")
    public Integer getDelayCounts() {
        return delayCounts;
    }

    public void setDelayCounts(Integer delayCounts) {
        this.delayCounts = delayCounts;
    }

    @Column(name = "buss_day")
    public Date getBussDay() {
        return bussDay;
    }

    public void setBussDay(Date bussDay) {
        this.bussDay = bussDay;
    }

    @Column(name = "expire_time")
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Column(name = "buss_identity_key")
    public String getBussIdentityKey() {
        return bussIdentityKey;
    }

    public void setBussIdentityKey(String bussIdentityKey) {
        this.bussIdentityKey = bussIdentityKey;
    }

    @Column(name = "source_type")
    public UserCalendarSource getSourceType() {
        return sourceType;
    }

    public void setSourceType(UserCalendarSource sourceType) {
        this.sourceType = sourceType;
    }

    @Column(name = "source_id")
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "finished_time")
    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    @Column(name = "finish_type")
    public UserCalendarFinishType getFinishType() {
        return finishType;
    }

    public void setFinishType(UserCalendarFinishType finishType) {
        this.finishType = finishType;
    }

    @Basic
    @Column(name = "message_id")
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "finish_source_id")
    public Long getFinishSourceId() {
        return finishSourceId;
    }

    public void setFinishSourceId(Long finishSourceId) {
        this.finishSourceId = finishSourceId;
    }

    @Basic
    @Column(name = "all_day")
    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public String getLocation() {
        return location;
    }

    @Column(name = "location")
    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "read_only")
    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Column(name = "period")
    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    @Column(name = "period_values")
    public String getPeriodValues() {
        return periodValues;
    }

    public void setPeriodValues(String periodValues) {
        this.periodValues = periodValues;
    }

    @Column(name = "calendar_config_id")
    public Long getCalendarConfigId() {
        return calendarConfigId;
    }

    public void setCalendarConfigId(Long calendarConfigId) {
        this.calendarConfigId = calendarConfigId;
    }

    @Basic
    @Column(name = "bind_values")
    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
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

    @Transient
    public String getSourceTypeName() {
        return sourceType == null ? null : sourceType.getName();
    }

    @Transient
    public int getSourceTypeIndex() {
        return sourceType == null ? 0 : sourceType.getValue();
    }

    @Transient
    public String getFinishTypeName() {
        return finishType == null ? null : finishType.getName();
    }

    @Transient
    public String getPeriodName() {
        return period == null ? null : period.getName();
    }

}
