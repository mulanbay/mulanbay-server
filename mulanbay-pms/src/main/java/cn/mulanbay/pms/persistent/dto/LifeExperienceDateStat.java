package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.dto.common.CalendarDateStat;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LifeExperienceDateStat implements DateStat, CalendarDateStat {

    private Integer indexValue;
    private BigInteger totalCount;
    private BigDecimal totalDays;

    @Override
    public Integer getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Integer indexValue) {
        this.indexValue = indexValue;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(BigDecimal totalDays) {
        this.totalDays = totalDays;
    }

    @Override
    public double getCalendarStatValue() {
        return totalDays.doubleValue();
    }

    @Override
    public int getDateIndexValue() {
        return indexValue.intValue();
    }
}
