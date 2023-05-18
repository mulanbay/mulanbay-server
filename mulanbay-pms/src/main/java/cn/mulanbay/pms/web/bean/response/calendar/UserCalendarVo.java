package cn.mulanbay.pms.web.bean.response.calendar;

import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.enums.UserCalendarFinishType;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;

import java.util.Date;

public class UserCalendarVo {

    /**
     * 因为有很多的来源，这里的ID=sourceType+sourceId
     */
    private String id;

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

    private String value;

    private String unit;

    /**
     * 日志流水是否和原日历设置一样
     */
    private boolean match = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDelayCounts() {
        return delayCounts;
    }

    public void setDelayCounts(Integer delayCounts) {
        this.delayCounts = delayCounts;
    }

    public Date getBussDay() {
        return bussDay;
    }

    public void setBussDay(Date bussDay) {
        this.bussDay = bussDay;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getBussIdentityKey() {
        return bussIdentityKey;
    }

    public void setBussIdentityKey(String bussIdentityKey) {
        this.bussIdentityKey = bussIdentityKey;
    }

    public UserCalendarSource getSourceType() {
        return sourceType;
    }

    public void setSourceType(UserCalendarSource sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public UserCalendarFinishType getFinishType() {
        return finishType;
    }

    public void setFinishType(UserCalendarFinishType finishType) {
        this.finishType = finishType;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getFinishSourceId() {
        return finishSourceId;
    }

    public void setFinishSourceId(Long finishSourceId) {
        this.finishSourceId = finishSourceId;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public String getPeriodValues() {
        return periodValues;
    }

    public void setPeriodValues(String periodValues) {
        this.periodValues = periodValues;
    }

    public Long getCalendarConfigId() {
        return calendarConfigId;
    }

    public void setCalendarConfigId(Long calendarConfigId) {
        this.calendarConfigId = calendarConfigId;
    }

    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getSourceTypeName() {
        return sourceType == null ? null : sourceType.getName();
    }

    public int getSourceTypeIndex() {
        return sourceType == null ? 0 : sourceType.getValue();
    }

    public String getFinishTypeName() {
        return finishType == null ? null : finishType.getName();
    }

    public String getPeriodName() {
        return period == null ? null : period.getName();
    }
}
