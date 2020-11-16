package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.PeriodType;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class BudgetAnalyseSearch implements BindUser {

    @NotNull(message = "{validate.stat.date.NotNull}")
    private Date date;

    public Long userId;

    private Long userBehaviorId;

    private PeriodType period;

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

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public Long getUserBehaviorId() {
        return userBehaviorId;
    }

    public void setUserBehaviorId(Long userBehaviorId) {
        this.userBehaviorId = userBehaviorId;
    }

}
