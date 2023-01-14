package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.dto.common.CalendarDateStat;

import java.math.BigDecimal;
import java.math.BigInteger;

public class IncomeTypeStat implements CalendarDateStat {
    // 月份
    private Short indexValue;
    private BigDecimal totalAmount;
    private BigInteger totalCount;

    public Short getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Short indexValue) {
        this.indexValue = indexValue;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public double getCalendarStatValue() {
        return totalAmount.doubleValue();
    }

    @Override
    public int getDayIndexValue() {
        return indexValue.intValue();
    }
}
