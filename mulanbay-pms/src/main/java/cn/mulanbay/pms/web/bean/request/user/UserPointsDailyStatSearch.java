package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;

import java.util.Date;

public class UserPointsDailyStatSearch implements BindUser {

    private Date date;

    private Long userId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
