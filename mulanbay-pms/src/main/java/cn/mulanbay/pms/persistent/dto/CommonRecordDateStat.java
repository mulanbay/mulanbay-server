package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.dto.common.CalendarDateStat;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CommonRecordDateStat implements DateStat, CalendarDateStat {

    private Integer indexValue;
    private BigInteger totalCount;
    private BigDecimal totalValue;

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

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public double getCalendarStatValue() {
        return totalValue.doubleValue();
    }

    @Override
    public int getDateIndexValue() {
        return indexValue.intValue();
    }
}
