package cn.mulanbay.pms.web.bean.request.usercalendar;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserCalendarCreate2Request implements BindUser {

    private Long userId;

    @NotEmpty(message = "{validate.userCalendar.title.notEmpty}")
    private String title;

    @NotNull(message = "{validate.userCalendar.bussDay.NotNull}")
    private Date bussDay;

    //失效时间
    @NotNull(message = "{validate.userCalendar.expireTime.NotNull}")
    private Date expireTime;

    //是否为全天任务
    private Boolean allDay;

    //手动设置有用
    private String location;

    private UserCalendarSource calendarId;

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

    public UserCalendarSource getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(UserCalendarSource calendarId) {
        this.calendarId = calendarId;
    }
}
