package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;

public class BudgetSnapshotChildrenSearch implements BindUser {

    @Query(fieldName = "snapshotId", op = Parameter.Operator.EQ)
    private Long snapshotId;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    public Long getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Long snapshotId) {
        this.snapshotId = snapshotId;
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
