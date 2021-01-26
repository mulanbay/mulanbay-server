package cn.mulanbay.pms.web.bean.request.sleep;

import cn.mulanbay.common.aop.BindUser;

import java.util.Date;

public class SleepFormRequest implements BindUser {

    private Long id;

    private Long userId;

    //睡觉时间
    private Date sleepTime;

    //起床时间
    private Date getUpTime;

    //浅睡时长（分钟）
    private Integer lightSleep;

    //深睡时长（分钟）
    private Integer deepSleep;

    private Integer quality;

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

    public Date getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Date sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Date getGetUpTime() {
        return getUpTime;
    }

    public void setGetUpTime(Date getUpTime) {
        this.getUpTime = getUpTime;
    }

    public Integer getLightSleep() {
        return lightSleep;
    }

    public void setLightSleep(Integer lightSleep) {
        this.lightSleep = lightSleep;
    }

    public Integer getDeepSleep() {
        return deepSleep;
    }

    public void setDeepSleep(Integer deepSleep) {
        this.deepSleep = deepSleep;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
