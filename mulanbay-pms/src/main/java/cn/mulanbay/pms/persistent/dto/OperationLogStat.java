package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

public class OperationLogStat {

    private String name;

    private BigInteger totalCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }
}
