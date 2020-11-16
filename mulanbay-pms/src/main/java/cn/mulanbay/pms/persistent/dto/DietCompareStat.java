package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.enums.DietSource;
import cn.mulanbay.pms.persistent.enums.DietType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DietCompareStat {

    private DietType dietType;

    private DietSource dietSource;

    private BigInteger totalCount;

    private BigDecimal totalPrice;

    private BigInteger totalScore;

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public DietSource getDietSource() {
        return dietSource;
    }

    public void setDietSource(DietSource dietSource) {
        this.dietSource = dietSource;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigInteger getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigInteger totalScore) {
        this.totalScore = totalScore;
    }
}
