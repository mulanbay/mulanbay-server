package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SharesPriceAnalyseStat {

    //当前价格
    private BigDecimal currentPrice;

    //股票时间
    private Date occurTime;

    //换手率
    private BigDecimal turnOver;

    // 价格评分
    private Integer priceScore;

    // 资产评分
    private Integer assetScore;

    // 涨跌评分
    private Integer fgScore;

    // 售卖评分
    private Integer ssScore;

    // 风险评分
    private Integer riskScore;

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public BigDecimal getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(BigDecimal turnOver) {
        this.turnOver = turnOver;
    }

    public Integer getPriceScore() {
        return priceScore;
    }

    public void setPriceScore(Integer priceScore) {
        this.priceScore = priceScore;
    }

    public Integer getAssetScore() {
        return assetScore;
    }

    public void setAssetScore(Integer assetScore) {
        this.assetScore = assetScore;
    }

    public Integer getFgScore() {
        return fgScore;
    }

    public void setFgScore(Integer fgScore) {
        this.fgScore = fgScore;
    }

    public Integer getSsScore() {
        return ssScore;
    }

    public void setSsScore(Integer ssScore) {
        this.ssScore = ssScore;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public int getTotalScore() {
        if (priceScore == null) {
            return 0;
        }
        return priceScore + assetScore + fgScore + ssScore + riskScore;
    }

}
