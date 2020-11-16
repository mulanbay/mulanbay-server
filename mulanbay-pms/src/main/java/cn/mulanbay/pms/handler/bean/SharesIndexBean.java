package cn.mulanbay.pms.handler.bean;

import java.io.Serializable;

/**
 * 股票大盘
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class SharesIndexBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String code;

    // 当前点数
    private double point;

    //当前价格
    private double currentPrice = -1;

    //涨跌率
    private double fgRate;

    //成交量（手）
    private Long deals;

    //成交额（万元）
    private Double dealAmount;

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

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getFgRate() {
        return fgRate;
    }

    public void setFgRate(double fgRate) {
        this.fgRate = fgRate;
    }

    public Long getDeals() {
        return deals;
    }

    public void setDeals(Long deals) {
        this.deals = deals;
    }

    public Double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(Double dealAmount) {
        this.dealAmount = dealAmount;
    }
}
