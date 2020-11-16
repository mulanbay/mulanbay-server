package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserBehaviorHourStatSearch implements BindUser, FullEndDateTime {

    @NotNull(message = "{validate.stat.startDate.NotNull}")
    private Date startDate;

    @NotNull(message = "{validate.stat.endDate.NotNull}")
    private Date endDate;

    public Long userId;

    @NotNull(message = "{validate.stat.minHour.NotNull}")
    private int minHour;

    @NotNull(message = "{validate.stat.maxHour.NotNull}")
    private int maxHour;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getMinHour() {
        return minHour;
    }

    public void setMinHour(int minHour) {
        this.minHour = minHour;
    }

    public int getMaxHour() {
        return maxHour;
    }

    public void setMaxHour(int maxHour) {
        this.maxHour = maxHour;
    }
}
