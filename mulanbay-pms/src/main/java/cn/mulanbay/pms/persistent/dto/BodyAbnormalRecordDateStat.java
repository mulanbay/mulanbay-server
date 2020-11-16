package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.dto.common.CalendarDateStat;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BodyAbnormalRecordDateStat implements DateStat, CalendarDateStat {

    private Integer indexValue;
    private BigInteger totalCount;
    private BigDecimal totalLastDays;

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

    public BigDecimal getTotalLastDays() {
        return totalLastDays;
    }

    public void setTotalLastDays(BigDecimal totalLastDays) {
        this.totalLastDays = totalLastDays;
    }

    @Override
    public double getCalendarStatValue() {
        return totalLastDays.doubleValue();
    }

    @Override
    public int getDateIndexValue() {
        return indexValue.intValue();
    }
}
