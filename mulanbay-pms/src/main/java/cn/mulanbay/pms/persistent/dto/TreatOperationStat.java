package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class TreatOperationStat {

    private String name;

    private BigInteger totalCount;

    private BigDecimal totalFee;

    private Date minDate;

    private Date maxDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }
}
