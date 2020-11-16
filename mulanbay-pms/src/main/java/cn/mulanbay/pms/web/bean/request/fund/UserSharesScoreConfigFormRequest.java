package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;

public class UserSharesScoreConfigFormRequest implements BindUser {

    private Long id;
    private Long userId;

    //原始的购买价格分
    private String priceScoreConfig;

    //当前价格与购买价格分
    private String cbPriceScoreConfig;

    private String assetScoreConfig;

    private String fgScoreConfig;

    private String ssScoreConfig;

    private String turnOverScoreConfig;

    private String riskScoreConfig;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPriceScoreConfig() {
        return priceScoreConfig;
    }

    public void setPriceScoreConfig(String priceScoreConfig) {
        this.priceScoreConfig = priceScoreConfig;
    }

    public String getCbPriceScoreConfig() {
        return cbPriceScoreConfig;
    }

    public void setCbPriceScoreConfig(String cbPriceScoreConfig) {
        this.cbPriceScoreConfig = cbPriceScoreConfig;
    }

    public String getAssetScoreConfig() {
        return assetScoreConfig;
    }

    public void setAssetScoreConfig(String assetScoreConfig) {
        this.assetScoreConfig = assetScoreConfig;
    }

    public String getFgScoreConfig() {
        return fgScoreConfig;
    }

    public void setFgScoreConfig(String fgScoreConfig) {
        this.fgScoreConfig = fgScoreConfig;
    }

    public String getSsScoreConfig() {
        return ssScoreConfig;
    }

    public void setSsScoreConfig(String ssScoreConfig) {
        this.ssScoreConfig = ssScoreConfig;
    }

    public String getTurnOverScoreConfig() {
        return turnOverScoreConfig;
    }

    public void setTurnOverScoreConfig(String turnOverScoreConfig) {
        this.turnOverScoreConfig = turnOverScoreConfig;
    }

    public String getRiskScoreConfig() {
        return riskScoreConfig;
    }

    public void setRiskScoreConfig(String riskScoreConfig) {
        this.riskScoreConfig = riskScoreConfig;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
