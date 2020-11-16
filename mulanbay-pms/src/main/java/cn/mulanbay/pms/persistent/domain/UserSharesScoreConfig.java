package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户股票评分配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_shares_score_config")
public class UserSharesScoreConfig implements java.io.Serializable {

    private static final long serialVersionUID = 3389997742712399617L;

    private Long id;
    private Long userId;

    //原始的购买价格分
    private String priceScoreConfig = "0>>10=10,10>>30=5,30>>10000000=1";

    //当前价格与购买价格分
    private String cbPriceScoreConfig = "-10000>>-5=-30,-5>>-2=-20,-2>>0=-10,0>>2=10,2>>5=20,5>>10000=30";

    private String assetScoreConfig = "0>>5000=20,5000>>20000=10,20000>>10000000000=5";

    private String fgScoreConfig = "0>>1=0,1>>3=3,3>>5=5,5>>10000=8";

    private String ssScoreConfig = "-10000>>-100=-30,-100>>-50=-20,-20>>0=-10,0>>20=10,20>>50=20,50>>10000=30";

    //换手率
    private String turnOverScoreConfig = "0>>1=3,1>>3=5,3>>7=10,7>>15=5,15>>100=3";

    private String riskScoreConfig = "-10000>>-100=-30,-100>>-50=-20,-20>>0=-10,0>>20=10,20>>50=20,50>>10000=30";
    ;

    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "price_score_config")
    public String getPriceScoreConfig() {
        return priceScoreConfig;
    }

    public void setPriceScoreConfig(String priceScoreConfig) {
        this.priceScoreConfig = priceScoreConfig;
    }

    @Basic
    @Column(name = "cb_price_score_config")
    public String getCbPriceScoreConfig() {
        return cbPriceScoreConfig;
    }

    public void setCbPriceScoreConfig(String cbPriceScoreConfig) {
        this.cbPriceScoreConfig = cbPriceScoreConfig;
    }

    @Basic
    @Column(name = "asset_score_config")
    public String getAssetScoreConfig() {
        return assetScoreConfig;
    }

    public void setAssetScoreConfig(String assetScoreConfig) {
        this.assetScoreConfig = assetScoreConfig;
    }

    @Basic
    @Column(name = "fg_score_config")
    public String getFgScoreConfig() {
        return fgScoreConfig;
    }

    public void setFgScoreConfig(String fgScoreConfig) {
        this.fgScoreConfig = fgScoreConfig;
    }

    @Basic
    @Column(name = "ss_score_config")
    public String getSsScoreConfig() {
        return ssScoreConfig;
    }

    public void setSsScoreConfig(String ssScoreConfig) {
        this.ssScoreConfig = ssScoreConfig;
    }

    @Basic
    @Column(name = "turn_over_score_config")
    public String getTurnOverScoreConfig() {
        return turnOverScoreConfig;
    }

    public void setTurnOverScoreConfig(String turnOverScoreConfig) {
        this.turnOverScoreConfig = turnOverScoreConfig;
    }

    @Basic
    @Column(name = "risk_score_config")
    public String getRiskScoreConfig() {
        return riskScoreConfig;
    }

    public void setRiskScoreConfig(String riskScoreConfig) {
        this.riskScoreConfig = riskScoreConfig;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

}
