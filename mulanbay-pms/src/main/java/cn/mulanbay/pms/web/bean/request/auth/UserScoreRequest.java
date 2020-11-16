package cn.mulanbay.pms.web.bean.request.auth;

import cn.mulanbay.common.aop.BindUser;

import java.util.Date;

public class UserScoreRequest implements BindUser {

    private Long userId;

    private Date endDate;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
