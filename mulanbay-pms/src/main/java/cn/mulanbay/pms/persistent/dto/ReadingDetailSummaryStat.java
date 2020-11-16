package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ReadingDetailSummaryStat {

    private BigInteger totalCount;

    private BigDecimal totalMinutes;

    private double averageMinutes;

    public double getAverageMinutes() {
        return averageMinutes;
    }

    public void setAverageMinutes(double averageMinutes) {
        this.averageMinutes = averageMinutes;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(BigDecimal totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

}
