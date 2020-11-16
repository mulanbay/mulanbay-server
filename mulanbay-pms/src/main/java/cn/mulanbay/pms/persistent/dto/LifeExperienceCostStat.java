package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LifeExperienceCostStat {

    private BigInteger indexValue;
    private String name;
    private BigDecimal totalCost;

    public BigInteger getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(BigInteger indexValue) {
        this.indexValue = indexValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
