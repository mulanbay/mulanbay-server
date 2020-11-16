package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class MusicPracticeTuneNameStat {

    private String tune;

    private Date minPracticeDate;

    private Date maxPracticeDate;

    //次数
    private BigDecimal totalTimes;

    private BigInteger totalCounts;

    public Date getMinPracticeDate() {
        return minPracticeDate;
    }

    public void setMinPracticeDate(Date minPracticeDate) {
        this.minPracticeDate = minPracticeDate;
    }

    public Date getMaxPracticeDate() {
        return maxPracticeDate;
    }

    public void setMaxPracticeDate(Date maxPracticeDate) {
        this.maxPracticeDate = maxPracticeDate;
    }

    public BigDecimal getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(BigDecimal totalTimes) {
        this.totalTimes = totalTimes;
    }

    public String getTune() {
        return tune;
    }

    public void setTune(String tune) {
        this.tune = tune;
    }

    public BigInteger getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(BigInteger totalCounts) {
        this.totalCounts = totalCounts;
    }
}
