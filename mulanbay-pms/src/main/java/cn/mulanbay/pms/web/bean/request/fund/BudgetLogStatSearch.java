package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class BudgetLogStatSearch extends QueryBuilder implements DateStatSearch, BindUser, FullEndDateTime {

    @Query(fieldName = "occurDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occurDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "budget.id", op = Parameter.Operator.EQ)
    private Long budgetId;

    //页面传输使用，因为可能是budgetId，也可能是分组的编号
    private String budgetKey;

    private Boolean needOutBurst;

    /**
     * 是否要预测
     */
    private Boolean predict = false;

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

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getBudgetKey() {
        return budgetKey;
    }

    public void setBudgetKey(String budgetKey) {
        this.budgetKey = budgetKey;
    }

    public Boolean getNeedOutBurst() {
        return needOutBurst;
    }

    public void setNeedOutBurst(Boolean needOutBurst) {
        this.needOutBurst = needOutBurst;
    }

    public Boolean getPredict() {
        return predict;
    }

    public void setPredict(Boolean predict) {
        this.predict = predict;
    }
}
