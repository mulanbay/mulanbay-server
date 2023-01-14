package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

public class ReadingRecordDateStat implements DateStat {

    private Number indexValue;
    private BigInteger totalCount;

    @Override
    public Integer getDateIndexValue() {
        return indexValue==null ? null : indexValue.intValue();
    }

    public Number getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Number indexValue) {
        this.indexValue = indexValue;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

}
