package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

/**
 * Created by fenghong on 2017/8/30.
 */
public class DataInputAnalyseStat {

    private String groupName;

    private BigInteger totalCount;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }
}
