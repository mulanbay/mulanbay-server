package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SportExerciseOverallStat{

    private Integer indexValue;
    private BigDecimal totalKilometres;
    private BigInteger totalCount;
    private BigDecimal totalMinutes;
    private BigInteger sportTypeId;

    public Integer getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Integer indexValue) {
        this.indexValue = indexValue;
    }

    public BigDecimal getTotalKilometres() {
        return totalKilometres;
    }

    public void setTotalKilometres(BigDecimal totalKilometres) {
        this.totalKilometres = totalKilometres;
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

    public BigInteger getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(BigInteger sportTypeId) {
        this.sportTypeId = sportTypeId;
    }
}
