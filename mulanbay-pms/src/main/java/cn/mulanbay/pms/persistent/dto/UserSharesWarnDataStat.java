package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

public class UserSharesWarnDataStat {

    // 月份
    private Object groupId;
    private BigInteger totalCount;

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

}
