package cn.mulanbay.pms.web.bean.request.chart;

import cn.mulanbay.common.aop.BindUser;

import java.util.Date;

public class UserChartParaGetRequest implements BindUser {

    private Long userId;

    private Long userChartId;

    private Date startDate;

    private Date endDate;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserChartId() {
        return userChartId;
    }

    public void setUserChartId(Long userChartId) {
        this.userChartId = userChartId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
