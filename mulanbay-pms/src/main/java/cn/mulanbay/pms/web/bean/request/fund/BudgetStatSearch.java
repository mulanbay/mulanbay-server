package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.BudgetStatType;
import cn.mulanbay.pms.persistent.enums.BudgetType;
import cn.mulanbay.pms.persistent.enums.PeriodType;

public class BudgetStatSearch implements BindUser {

    private Long userId;

    private BudgetType type;
    //周期类型
    private PeriodType period;

    private BudgetStatType statType;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BudgetType getType() {
        return type;
    }

    public void setType(BudgetType type) {
        this.type = type;
    }

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public BudgetStatType getStatType() {
        return statType;
    }

    public void setStatType(BudgetStatType statType) {
        this.statType = statType;
    }
}
