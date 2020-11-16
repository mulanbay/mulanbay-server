package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LifeExperienceMapStat {

    private String name;

    //次数
    private BigInteger totalCount;

    //天数
    private BigInteger totalDays;

    private BigDecimal totalCost;

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

    public BigInteger getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(BigInteger totalDays) {
        this.totalDays = totalDays;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
