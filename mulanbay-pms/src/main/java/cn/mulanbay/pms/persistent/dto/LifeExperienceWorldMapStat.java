package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LifeExperienceWorldMapStat {

    private String country;

    private String countryLocation;
    //次数
    private BigInteger totalCount;

    //天数
    private BigInteger totalDays;

    private BigDecimal totalCost;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryLocation() {
        return countryLocation;
    }

    public void setCountryLocation(String countryLocation) {
        this.countryLocation = countryLocation;
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
