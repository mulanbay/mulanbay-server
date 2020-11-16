package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户股票评分
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_shares_score")
public class UserSharesScore implements java.io.Serializable {

    private static final long serialVersionUID = 7254329209805899896L;

    private Long id;
    private Long userId;
    //股票名称
    private UserShares userShares;
    private Long sharesPriceId;
    // 价格评分
    private int priceScore;

    // 资产评分
    private int assetScore;

    // 涨跌评分
    private int fgScore;

    // 售卖评分
    private int ssScore;

    // 风险评分
    private int riskScore;

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

    @ManyToOne
    @JoinColumn(name = "user_shares_id")
    public UserShares getUserShares() {
        return userShares;
    }

    public void setUserShares(UserShares userShares) {
        this.userShares = userShares;
    }

    @Basic
    @Column(name = "shares_price_id")
    public Long getSharesPriceId() {
        return sharesPriceId;
    }

    public void setSharesPriceId(Long sharesPriceId) {
        this.sharesPriceId = sharesPriceId;
    }

    @Basic
    @Column(name = "price_score")
    public int getPriceScore() {
        return priceScore;
    }

    public void setPriceScore(int priceScore) {
        this.priceScore = priceScore;
    }

    @Basic
    @Column(name = "asset_score")
    public int getAssetScore() {
        return assetScore;
    }

    public void setAssetScore(int assetScore) {
        this.assetScore = assetScore;
    }

    @Basic
    @Column(name = "fg_score")
    public int getFgScore() {
        return fgScore;
    }

    public void setFgScore(int fgScore) {
        this.fgScore = fgScore;
    }

    @Basic
    @Column(name = "ss_score")
    public int getSsScore() {
        return ssScore;
    }

    public void setSsScore(int ssScore) {
        this.ssScore = ssScore;
    }

    @Basic
    @Column(name = "risk_score")
    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
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

    /**
     * 总分
     *
     * @return
     */
    @Transient
    public int getTotalScore() {
        return priceScore + assetScore + fgScore + ssScore + riskScore;
    }

}
