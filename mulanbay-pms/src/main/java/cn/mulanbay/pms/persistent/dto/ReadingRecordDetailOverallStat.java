package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ReadingRecordDetailOverallStat {

    private Integer indexValue;
    private BigDecimal totalMinutes;
    private BigInteger bookCategoryId;

    public Integer getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Integer indexValue) {
        this.indexValue = indexValue;
    }

    public BigDecimal getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(BigDecimal totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public BigInteger getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(BigInteger bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }
}
