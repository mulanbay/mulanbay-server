package cn.mulanbay.pms.web.bean.request.sleep;

import cn.mulanbay.common.aop.BindUser;

import java.util.Date;

public class SleepRequest implements BindUser {

    private Long userId;
    //睡觉时间
    private Date sleepTime;

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

}
