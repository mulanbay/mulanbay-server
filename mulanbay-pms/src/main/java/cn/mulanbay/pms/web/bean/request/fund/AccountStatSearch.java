package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.AccountAnalyseType;
import cn.mulanbay.pms.persistent.enums.AccountStatus;
import cn.mulanbay.pms.persistent.enums.AccountType;

public class AccountStatSearch implements BindUser {

    private Long userId;

    private AccountAnalyseType groupType;

    private Long snapshotId;

    private AccountType type;

    private AccountStatus status;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AccountAnalyseType getGroupType() {
        return groupType;
    }

    public void setGroupType(AccountAnalyseType groupType) {
        this.groupType = groupType;
    }

    public Long getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Long snapshotId) {
        this.snapshotId = snapshotId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
