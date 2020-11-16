package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 股票的价格记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "shares_price")
public class SharesPrice implements java.io.Serializable {

    private static final long serialVersionUID = 7254329209805899896L;

    private Long id;
    private Long userId;
    //股票名称
    private String name;
    //股票代码
    private String code;
    //今日开盘价
    private Double openingPrice;
    //昨日收盘价
    private Double closingPrice;
    //当前价格
    private Double currentPrice;
    //今日最高价
    private Double maxPrice;
    //今日最低价
    private Double minPrice;
    //成交的股票数
    private Integer dealShares;
    //成交的金额
    private Double dealAmount;
    //买一的股票数
    private Integer buy1Shares;
    //买一的金额
    private Double buy1Price;
    //买二的股票数
    private Integer buy2Shares;
    //买二的金额
    private Double buy2Price;
    //买三的股票数
    private Integer buy3Shares;
    //买三的金额
    private Double buy3Price;
    //买四的股票数
    private Integer buy4Shares;
    //买四的金额
    private Double buy4Price;
    //买五的股票数
    private Integer buy5Shares;
    //买五的金额
    private Double buy5Price;
    //卖一的股票数
    private Integer sale1Shares;
    //卖一的金额
    private Double sale1Price;
    //卖二的股票数
    private Integer sale2Shares;
    //卖二的金额
    private Double sale2Price;
    //卖三的股票数
    private Integer sale3Shares;
    //卖三的金额
    private Double sale3Price;
    //卖四的股票数
    private Integer sale4Shares;
    //卖四的金额
    private Double sale4Price;
    //卖五的股票数
    private Integer sale5Shares;
    //卖五的金额
    private Double sale5Price;
    //股票时间
    private Date occurTime;
    //换手率
    private Double turnOver;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "opening_price")
    public Double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(Double openingPrice) {
        this.openingPrice = openingPrice;
    }

    @Basic
    @Column(name = "closing_price")
    public Double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(Double closingPrice) {
        this.closingPrice = closingPrice;
    }

    @Basic
    @Column(name = "current_price")
    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Basic
    @Column(name = "max_price")
    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Basic
    @Column(name = "min_price")
    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    @Basic
    @Column(name = "deal_shares")
    public Integer getDealShares() {
        return dealShares;
    }

    public void setDealShares(Integer dealShares) {
        this.dealShares = dealShares;
    }

    @Basic
    @Column(name = "deal_amount")
    public Double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(Double dealAmount) {
        this.dealAmount = dealAmount;
    }

    @Basic
    @Column(name = "buy_1_shares")
    public Integer getBuy1Shares() {
        return buy1Shares;
    }

    public void setBuy1Shares(Integer buy1Shares) {
        this.buy1Shares = buy1Shares;
    }

    @Basic
    @Column(name = "buy_1_price")
    public Double getBuy1Price() {
        return buy1Price;
    }

    public void setBuy1Price(Double buy1Price) {
        this.buy1Price = buy1Price;
    }

    @Basic
    @Column(name = "buy_2_shares")
    public Integer getBuy2Shares() {
        return buy2Shares;
    }

    public void setBuy2Shares(Integer buy2Shares) {
        this.buy2Shares = buy2Shares;
    }

    @Basic
    @Column(name = "buy_2_price")
    public Double getBuy2Price() {
        return buy2Price;
    }

    public void setBuy2Price(Double buy2Price) {
        this.buy2Price = buy2Price;
    }

    @Basic
    @Column(name = "buy_3_shares")
    public Integer getBuy3Shares() {
        return buy3Shares;
    }

    public void setBuy3Shares(Integer buy3Shares) {
        this.buy3Shares = buy3Shares;
    }

    @Basic
    @Column(name = "buy_3_price")
    public Double getBuy3Price() {
        return buy3Price;
    }

    public void setBuy3Price(Double buy3Price) {
        this.buy3Price = buy3Price;
    }

    @Basic
    @Column(name = "buy_4_shares")
    public Integer getBuy4Shares() {
        return buy4Shares;
    }

    public void setBuy4Shares(Integer buy4Shares) {
        this.buy4Shares = buy4Shares;
    }

    @Basic
    @Column(name = "buy_4_price")
    public Double getBuy4Price() {
        return buy4Price;
    }

    public void setBuy4Price(Double buy4Price) {
        this.buy4Price = buy4Price;
    }

    @Basic
    @Column(name = "buy_5_shares")
    public Integer getBuy5Shares() {
        return buy5Shares;
    }

    public void setBuy5Shares(Integer buy5Shares) {
        this.buy5Shares = buy5Shares;
    }

    @Basic
    @Column(name = "buy_5_price")
    public Double getBuy5Price() {
        return buy5Price;
    }

    public void setBuy5Price(Double buy5Price) {
        this.buy5Price = buy5Price;
    }

    @Basic
    @Column(name = "sale_1_shares")
    public Integer getSale1Shares() {
        return sale1Shares;
    }

    public void setSale1Shares(Integer sale1Shares) {
        this.sale1Shares = sale1Shares;
    }

    @Basic
    @Column(name = "sale_1_price")
    public Double getSale1Price() {
        return sale1Price;
    }

    public void setSale1Price(Double sale1Price) {
        this.sale1Price = sale1Price;
    }

    @Basic
    @Column(name = "sale_2_shares")
    public Integer getSale2Shares() {
        return sale2Shares;
    }

    public void setSale2Shares(Integer sale2Shares) {
        this.sale2Shares = sale2Shares;
    }

    @Basic
    @Column(name = "sale_2_price")
    public Double getSale2Price() {
        return sale2Price;
    }

    public void setSale2Price(Double sale2Price) {
        this.sale2Price = sale2Price;
    }

    @Basic
    @Column(name = "sale_3_shares")
    public Integer getSale3Shares() {
        return sale3Shares;
    }

    public void setSale3Shares(Integer sale3Shares) {
        this.sale3Shares = sale3Shares;
    }

    @Basic
    @Column(name = "sale_3_price")
    public Double getSale3Price() {
        return sale3Price;
    }

    public void setSale3Price(Double sale3Price) {
        this.sale3Price = sale3Price;
    }

    @Basic
    @Column(name = "sale_4_shares")
    public Integer getSale4Shares() {
        return sale4Shares;
    }

    public void setSale4Shares(Integer sale4Shares) {
        this.sale4Shares = sale4Shares;
    }

    @Basic
    @Column(name = "sale_4_price")
    public Double getSale4Price() {
        return sale4Price;
    }

    public void setSale4Price(Double sale4Price) {
        this.sale4Price = sale4Price;
    }

    @Basic
    @Column(name = "sale_5_shares")
    public Integer getSale5Shares() {
        return sale5Shares;
    }

    public void setSale5Shares(Integer sale5Shares) {
        this.sale5Shares = sale5Shares;
    }

    @Basic
    @Column(name = "sale_5_price")
    public Double getSale5Price() {
        return sale5Price;
    }

    public void setSale5Price(Double sale5Price) {
        this.sale5Price = sale5Price;
    }

    @Basic
    @Column(name = "occur_time")
    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    @Basic
    @Column(name = "turn_over")
    public Double getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(Double turnOver) {
        this.turnOver = turnOver;
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
