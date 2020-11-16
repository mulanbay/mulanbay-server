package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SportExerciseMultiStat {

    //冗余字段，方便前端操作
    private BigInteger sportTypeId;
    //最佳值
    private BigDecimal maxKilometres;
    private Integer maxMinutes;
    private BigDecimal maxSpeed;
    private BigDecimal maxMaxSpeed;
    private BigDecimal maxPace;
    private BigDecimal maxMaxPace;
    private Integer maxMaxHeartRate;
    private Integer maxAverageHeartRate;
    //结束
    //最小值
    private BigDecimal minKilometres;
    private Integer minMinutes;
    private BigDecimal minSpeed;
    private BigDecimal minMaxSpeed;
    private BigDecimal minPace;
    private BigDecimal minMaxPace;
    private Integer minMaxHeartRate;
    private Integer minAverageHeartRate;
    //结束
    //平均值(有小数)
    private BigDecimal avgKilometres;
    private BigDecimal avgMinutes;
    private BigDecimal avgSpeed;
    private BigDecimal avgMaxSpeed;
    private BigDecimal avgPace;
    private BigDecimal avgMaxPace;
    private BigDecimal avgMaxHeartRate;
    private BigDecimal avgAverageHeartRate;
    //结束

    private String unit;

    public BigInteger getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(BigInteger sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    public BigDecimal getMaxKilometres() {
        return maxKilometres;
    }

    public void setMaxKilometres(BigDecimal maxKilometres) {
        this.maxKilometres = maxKilometres;
    }

    public Integer getMaxMinutes() {
        return maxMinutes;
    }

    public void setMaxMinutes(Integer maxMinutes) {
        this.maxMinutes = maxMinutes;
    }

    public BigDecimal getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(BigDecimal maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public BigDecimal getMaxMaxSpeed() {
        return maxMaxSpeed;
    }

    public void setMaxMaxSpeed(BigDecimal maxMaxSpeed) {
        this.maxMaxSpeed = maxMaxSpeed;
    }

    public BigDecimal getMaxPace() {
        return maxPace;
    }

    public void setMaxPace(BigDecimal maxPace) {
        this.maxPace = maxPace;
    }

    public BigDecimal getMaxMaxPace() {
        return maxMaxPace;
    }

    public void setMaxMaxPace(BigDecimal maxMaxPace) {
        this.maxMaxPace = maxMaxPace;
    }

    public Integer getMaxMaxHeartRate() {
        return maxMaxHeartRate;
    }

    public void setMaxMaxHeartRate(Integer maxMaxHeartRate) {
        this.maxMaxHeartRate = maxMaxHeartRate;
    }

    public Integer getMaxAverageHeartRate() {
        return maxAverageHeartRate;
    }

    public void setMaxAverageHeartRate(Integer maxAverageHeartRate) {
        this.maxAverageHeartRate = maxAverageHeartRate;
    }

    public BigDecimal getMinKilometres() {
        return minKilometres;
    }

    public void setMinKilometres(BigDecimal minKilometres) {
        this.minKilometres = minKilometres;
    }

    public Integer getMinMinutes() {
        return minMinutes;
    }

    public void setMinMinutes(Integer minMinutes) {
        this.minMinutes = minMinutes;
    }

    public BigDecimal getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(BigDecimal minSpeed) {
        this.minSpeed = minSpeed;
    }

    public BigDecimal getMinMaxSpeed() {
        return minMaxSpeed;
    }

    public void setMinMaxSpeed(BigDecimal minMaxSpeed) {
        this.minMaxSpeed = minMaxSpeed;
    }

    public BigDecimal getMinPace() {
        return minPace;
    }

    public void setMinPace(BigDecimal minPace) {
        this.minPace = minPace;
    }

    public BigDecimal getMinMaxPace() {
        return minMaxPace;
    }

    public void setMinMaxPace(BigDecimal minMaxPace) {
        this.minMaxPace = minMaxPace;
    }

    public Integer getMinMaxHeartRate() {
        return minMaxHeartRate;
    }

    public void setMinMaxHeartRate(Integer minMaxHeartRate) {
        this.minMaxHeartRate = minMaxHeartRate;
    }

    public Integer getMinAverageHeartRate() {
        return minAverageHeartRate;
    }

    public void setMinAverageHeartRate(Integer minAverageHeartRate) {
        this.minAverageHeartRate = minAverageHeartRate;
    }

    public BigDecimal getAvgKilometres() {
        return avgKilometres;
    }

    public void setAvgKilometres(BigDecimal avgKilometres) {
        this.avgKilometres = avgKilometres;
    }

    public BigDecimal getAvgMinutes() {
        return avgMinutes;
    }

    public void setAvgMinutes(BigDecimal avgMinutes) {
        this.avgMinutes = avgMinutes;
    }

    public BigDecimal getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(BigDecimal avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public BigDecimal getAvgMaxSpeed() {
        return avgMaxSpeed;
    }

    public void setAvgMaxSpeed(BigDecimal avgMaxSpeed) {
        this.avgMaxSpeed = avgMaxSpeed;
    }

    public BigDecimal getAvgPace() {
        return avgPace;
    }

    public void setAvgPace(BigDecimal avgPace) {
        this.avgPace = avgPace;
    }

    public BigDecimal getAvgMaxPace() {
        return avgMaxPace;
    }

    public void setAvgMaxPace(BigDecimal avgMaxPace) {
        this.avgMaxPace = avgMaxPace;
    }

    public BigDecimal getAvgMaxHeartRate() {
        return avgMaxHeartRate;
    }

    public void setAvgMaxHeartRate(BigDecimal avgMaxHeartRate) {
        this.avgMaxHeartRate = avgMaxHeartRate;
    }

    public BigDecimal getAvgAverageHeartRate() {
        return avgAverageHeartRate;
    }

    public void setAvgAverageHeartRate(BigDecimal avgAverageHeartRate) {
        this.avgAverageHeartRate = avgAverageHeartRate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
