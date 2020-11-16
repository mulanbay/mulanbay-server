package cn.mulanbay.pms.handler.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 股票价格
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class SharesPriceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String code;

    private double currentPrice = -1;

    //今日开盘价
    private Double openingPrice;
    //昨日收盘价
    private Double closingPrice;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(Double openingPrice) {
        this.openingPrice = openingPrice;
    }

    public Double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(Double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getDealShares() {
        return dealShares;
    }

    public void setDealShares(Integer dealShares) {
        this.dealShares = dealShares;
    }

    public Double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(Double dealAmount) {
        this.dealAmount = dealAmount;
    }

    public Integer getBuy1Shares() {
        return buy1Shares;
    }

    public void setBuy1Shares(Integer buy1Shares) {
        this.buy1Shares = buy1Shares;
    }

    public Double getBuy1Price() {
        return buy1Price;
    }

    public void setBuy1Price(Double buy1Price) {
        this.buy1Price = buy1Price;
    }

    public Integer getBuy2Shares() {
        return buy2Shares;
    }

    public void setBuy2Shares(Integer buy2Shares) {
        this.buy2Shares = buy2Shares;
    }

    public Double getBuy2Price() {
        return buy2Price;
    }

    public void setBuy2Price(Double buy2Price) {
        this.buy2Price = buy2Price;
    }

    public Integer getBuy3Shares() {
        return buy3Shares;
    }

    public void setBuy3Shares(Integer buy3Shares) {
        this.buy3Shares = buy3Shares;
    }

    public Double getBuy3Price() {
        return buy3Price;
    }

    public void setBuy3Price(Double buy3Price) {
        this.buy3Price = buy3Price;
    }

    public Integer getBuy4Shares() {
        return buy4Shares;
    }

    public void setBuy4Shares(Integer buy4Shares) {
        this.buy4Shares = buy4Shares;
    }

    public Double getBuy4Price() {
        return buy4Price;
    }

    public void setBuy4Price(Double buy4Price) {
        this.buy4Price = buy4Price;
    }

    public Integer getBuy5Shares() {
        return buy5Shares;
    }

    public void setBuy5Shares(Integer buy5Shares) {
        this.buy5Shares = buy5Shares;
    }

    public Double getBuy5Price() {
        return buy5Price;
    }

    public void setBuy5Price(Double buy5Price) {
        this.buy5Price = buy5Price;
    }

    public Integer getSale1Shares() {
        return sale1Shares;
    }

    public void setSale1Shares(Integer sale1Shares) {
        this.sale1Shares = sale1Shares;
    }

    public Double getSale1Price() {
        return sale1Price;
    }

    public void setSale1Price(Double sale1Price) {
        this.sale1Price = sale1Price;
    }

    public Integer getSale2Shares() {
        return sale2Shares;
    }

    public void setSale2Shares(Integer sale2Shares) {
        this.sale2Shares = sale2Shares;
    }

    public Double getSale2Price() {
        return sale2Price;
    }

    public void setSale2Price(Double sale2Price) {
        this.sale2Price = sale2Price;
    }

    public Integer getSale3Shares() {
        return sale3Shares;
    }

    public void setSale3Shares(Integer sale3Shares) {
        this.sale3Shares = sale3Shares;
    }

    public Double getSale3Price() {
        return sale3Price;
    }

    public void setSale3Price(Double sale3Price) {
        this.sale3Price = sale3Price;
    }

    public Integer getSale4Shares() {
        return sale4Shares;
    }

    public void setSale4Shares(Integer sale4Shares) {
        this.sale4Shares = sale4Shares;
    }

    public Double getSale4Price() {
        return sale4Price;
    }

    public void setSale4Price(Double sale4Price) {
        this.sale4Price = sale4Price;
    }

    public Integer getSale5Shares() {
        return sale5Shares;
    }

    public void setSale5Shares(Integer sale5Shares) {
        this.sale5Shares = sale5Shares;
    }

    public Double getSale5Price() {
        return sale5Price;
    }

    public void setSale5Price(Double sale5Price) {
        this.sale5Price = sale5Price;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public Double getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(Double turnOver) {
        this.turnOver = turnOver;
    }
}
