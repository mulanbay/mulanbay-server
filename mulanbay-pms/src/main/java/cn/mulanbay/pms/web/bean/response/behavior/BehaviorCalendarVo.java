package cn.mulanbay.pms.web.bean.response.behavior;

import cn.mulanbay.pms.persistent.enums.UserBehaviorType;

import java.util.Date;

public class BehaviorCalendarVo {

    private Long id;

    private String title;

    private String content;

    //延迟次数
    private Integer delayCounts = 0;

    private Date bussDay;

    //失效时间
    private Date expireTime;

    private UserBehaviorType behaviorType;

    //是否为全天任务
    private Boolean allDay;

    //手动设置有用
    private String location;

    private Boolean readOnly;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
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

    public int getBehaviorTypeIndex() {
        return behaviorType == null ? 0 : behaviorType.getValue();
    }

    public String getBehaviorTypeName() {
        return behaviorType == null ? null : behaviorType.getName();
    }

}
