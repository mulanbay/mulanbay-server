package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.dto.common.CalendarDateStat;

import java.math.BigInteger;

public class UserSharesWarnDateStat implements DateStat, CalendarDateStat {

    private Short warnType;
    private Integer indexValue;
    private BigInteger totalCount;

    public Short getWarnType() {
        return warnType;
    }

    public void setWarnType(Short warnType) {
        this.warnType = warnType;
    }

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

    @Override
    public double getCalendarStatValue() {
        return totalCount.doubleValue();
    }

    @Override
    public int getDateIndexValue() {
        return indexValue.intValue();
    }
}
