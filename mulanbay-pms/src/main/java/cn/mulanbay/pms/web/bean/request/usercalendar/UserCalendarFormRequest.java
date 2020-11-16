package cn.mulanbay.pms.web.bean.request.usercalendar;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.enums.UserCalendarFinishType;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserCalendarFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotEmpty(message = "{validate.userCalendar.title.notEmpty}")
    private String title;

    @NotEmpty(message = "{validate.userCalendar.content.notEmpty}")
    private String content;

    @NotNull(message = "{validate.userCalendar.bussDay.NotNull}")
    private Date bussDay;

    //失效时间
    //@NotNull(message = "{validate.userCalendar.expireTime.NotNull}")
    private Date expireTime;

    //唯一key
    private String bussIdentityKey;

    @NotNull(message = "{validate.userCalendar.sourceType.NotNull}")
    private UserCalendarSource sourceType;

    private String sourceId;

    private Date finishedTime;

    private UserCalendarFinishType finishType;

    //是否为全天任务
    private Boolean allDay;

    //手动设置有用
    private String location;

    @NotNull(message = "{validate.userCalendar.period.NotNull}")
    private PeriodType period;

    private String periodValues;

    //以模板新增的，可以查询是否完成的判断
    private Long calendarConfigId;

    //用户自己选择的值，以模板新增的，可以查询是否完成的判断
    private String bindValues;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
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
}
