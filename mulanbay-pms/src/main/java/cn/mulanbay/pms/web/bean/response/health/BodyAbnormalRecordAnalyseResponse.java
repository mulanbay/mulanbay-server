package cn.mulanbay.pms.web.bean.response.health;

import cn.mulanbay.pms.persistent.dto.TreatRecordAnalyseDetailStat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class BodyAbnormalRecordAnalyseResponse {

    private long id;
    //器官名称
    private String name;

    //持续次数
    private BigInteger totalCount;

    //持续天数
    private BigDecimal totalLastDays;

    private Date maxOccurDate;

    private Date minOccurDate;

    private TreatRecordAnalyseDetailStat treatRecordStat;

    //平均体重
    private BigDecimal avgWeight;

    //平均身高
    private BigDecimal avgHeight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public BigDecimal getTotalLastDays() {
        return totalLastDays;
    }

    public void setTotalLastDays(BigDecimal totalLastDays) {
        this.totalLastDays = totalLastDays;
    }

    public Date getMaxOccurDate() {
        return maxOccurDate;
    }

    public void setMaxOccurDate(Date maxOccurDate) {
        this.maxOccurDate = maxOccurDate;
    }

    public Date getMinOccurDate() {
        return minOccurDate;
    }

    public void setMinOccurDate(Date minOccurDate) {
        this.minOccurDate = minOccurDate;
    }

    public TreatRecordAnalyseDetailStat getTreatRecordStat() {
        return treatRecordStat;
    }

    public void setTreatRecordStat(TreatRecordAnalyseDetailStat treatRecordStat) {
        this.treatRecordStat = treatRecordStat;
    }

    public BigDecimal getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(BigDecimal avgWeight) {
        this.avgWeight = avgWeight;
    }

    public BigDecimal getAvgHeight() {
        return avgHeight;
    }

    public void setAvgHeight(BigDecimal avgHeight) {
        this.avgHeight = avgHeight;
    }
}
