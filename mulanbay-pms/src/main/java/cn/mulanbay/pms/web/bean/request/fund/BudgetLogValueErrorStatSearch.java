package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.NullType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class BudgetLogValueErrorStatSearch extends QueryBuilder implements DateStatSearch, BindUser, FullEndDateTime {

    @Query(fieldName = "occurDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occurDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "period", op = Parameter.Operator.EQ)
    private PeriodType period;

    @Query(fieldName = "accountChangeAmount", op = Parameter.Operator.NULL_NOTNULL)
    private NullType accountChangeAmount;

    @Override
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
    public DateGroupType getDateGroupType() {
        return null;
    }

    @Override
    public Boolean isCompliteDate() {
        return null;
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

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public NullType getAccountChangeAmount() {
        return accountChangeAmount;
    }

    public void setAccountChangeAmount(NullType accountChangeAmount) {
        this.accountChangeAmount = accountChangeAmount;
    }
}
