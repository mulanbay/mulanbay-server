package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.domain.TreatRecord;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class TreatRecordAnalyseDetailStat {

    private BigInteger totalCount;

    private BigDecimal totalFee;

    private Date minTreatDate;

    private Date maxTreatDate;

    //最早一次
    private TreatRecord minTreatRecord;

    //最近一次
    private TreatRecord maxTreatRecord;

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

    public Date getMinTreatDate() {
        return minTreatDate;
    }

    public void setMinTreatDate(Date minTreatDate) {
        this.minTreatDate = minTreatDate;
    }

    public Date getMaxTreatDate() {
        return maxTreatDate;
    }

    public void setMaxTreatDate(Date maxTreatDate) {
        this.maxTreatDate = maxTreatDate;
    }

    public TreatRecord getMinTreatRecord() {
        return minTreatRecord;
    }

    public void setMinTreatRecord(TreatRecord minTreatRecord) {
        this.minTreatRecord = minTreatRecord;
    }

    public TreatRecord getMaxTreatRecord() {
        return maxTreatRecord;
    }

    public void setMaxTreatRecord(TreatRecord maxTreatRecord) {
        this.maxTreatRecord = maxTreatRecord;
    }
}
