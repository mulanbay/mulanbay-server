package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;

public class BudgetSnapshotListSearch implements BindUser {

    @Query(fieldName = "budgetLogId", op = Parameter.Operator.EQ)
    private Long budgetLogId;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    public Long getBudgetLogId() {
        return budgetLogId;
    }

    public void setBudgetLogId(Long budgetLogId) {
        this.budgetLogId = budgetLogId;
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
