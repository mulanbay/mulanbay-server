package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class WorkOvertimeStat {

    private BigInteger totalCount;

    private BigDecimal totalHours;

    private BigDecimal averageHours;

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
    }

    public BigDecimal getAverageHours() {
        return averageHours;
    }

    public void setAverageHours(BigDecimal averageHours) {
        this.averageHours = averageHours;
    }
}
