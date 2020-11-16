package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.dto.common.CalendarDateStat;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SportExerciseDateStat implements DateStat, CalendarDateStat {

    private Integer indexValue;
    private BigDecimal totalKilometres;
    private BigInteger totalCount;
    private BigDecimal totalMaxHeartRate;
    private BigDecimal totalAverageHeartRate;
    private BigDecimal totalSpeed;
    private BigDecimal totalMinutes;
    private BigDecimal totalPace;


    @Override
    public double getCalendarStatValue() {
        return totalKilometres.doubleValue();
    }

    @Override
    public int getDateIndexValue() {
        return indexValue.intValue();
    }

    @Override
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

    public BigDecimal getTotalMaxHeartRate() {
        return totalMaxHeartRate;
    }

    public void setTotalMaxHeartRate(BigDecimal totalMaxHeartRate) {
        this.totalMaxHeartRate = totalMaxHeartRate;
    }

    public BigDecimal getTotalAverageHeartRate() {
        return totalAverageHeartRate;
    }

    public void setTotalAverageHeartRate(BigDecimal totalAverageHeartRate) {
        this.totalAverageHeartRate = totalAverageHeartRate;
    }

    public BigDecimal getTotalSpeed() {
        return totalSpeed;
    }

    public void setTotalSpeed(BigDecimal totalSpeed) {
        this.totalSpeed = totalSpeed;
    }

    public BigDecimal getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(BigDecimal totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public BigDecimal getTotalPace() {
        return totalPace;
    }

    public void setTotalPace(BigDecimal totalPace) {
        this.totalPace = totalPace;
    }
}
