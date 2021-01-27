package cn.mulanbay.pms.web.bean.request.sleep;

import cn.mulanbay.common.aop.BindUser;

import java.util.Date;

public class SleepGetUpRequest implements BindUser {

    private Long userId;
    //睡觉时间
    private Date getUpTime;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getGetUpTime() {
        return getUpTime;
    }

    public void setGetUpTime(Date getUpTime) {
        this.getUpTime = getUpTime;
    }
}
