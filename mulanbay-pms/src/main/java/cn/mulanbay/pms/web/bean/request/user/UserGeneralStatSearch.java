package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserGeneralStatSearch implements BindUser, FullEndDateTime {

    private Long userId;

    @NotNull(message = "{validate.stat.startTime.NotNull}")
    private Date startDate;

    @NotNull(message = "{validate.stat.endTime.NotNull}")
    private Date endDate;

    //sql需要原始的数字类型
    private Short consumeType;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Short getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(Short consumeType) {
        this.consumeType = consumeType;
    }
}
