package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

public class ReadingRecordOverallStat {

    private Integer indexValue;
    private BigInteger totalCount;
    private BigInteger bookCategoryId;

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

    public BigInteger getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(BigInteger bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }
}
