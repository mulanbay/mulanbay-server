package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SportExerciseStat {

    private BigInteger totalCount;

    private BigDecimal totalMinutes;

    private BigDecimal totalKilometres;

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

    public BigDecimal getTotalKilometres() {
        return totalKilometres;
    }

    public void setTotalKilometres(BigDecimal totalKilometres) {
        this.totalKilometres = totalKilometres;
    }
}
