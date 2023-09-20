package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BuyRecordUseTimeStat {

    private Object name;
    private BigDecimal totalUseTime;
    private BigInteger totalCount;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public BigDecimal getTotalUseTime() {
        return totalUseTime;
    }

    public void setTotalUseTime(BigDecimal totalUseTime) {
        this.totalUseTime = totalUseTime;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }
}
