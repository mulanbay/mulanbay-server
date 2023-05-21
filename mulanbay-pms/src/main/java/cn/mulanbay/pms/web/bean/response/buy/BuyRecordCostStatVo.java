package cn.mulanbay.pms.web.bean.response.buy;

import java.util.Date;

public class BuyRecordCostStatVo {

    private Long id;

    /**
     * 商品的价格（不包含下一级商品）
     */
    private Double totalPrice;

    /**
     * 购买日期
     */
    private Date buyDate;

    /**
     * 消费日期(比如音乐会门票购买日期和实际消费日期不一样)
     */
    private Date consumeDate;

    /**
     * 售出价格（单位：元）
     */
    private Double soldPrice;

    /**
     * 作废日期
     */
    private Date deleteDate;

    /**
     * 预期作废日期
     */
    private Date expectDeleteDate;

    /**
     * 下级商品总成本(价格)
     */
    private Double childrenPrice;

    /**
     * 下级商品总售出价格
     */
    private Double childrenSoldPrice;

    /**
     * 下级商品数
     */
    private Long childrens;

    /**
     * 总成本
     */
    private Double totalCost;

    /**
     * 使用时长(毫秒)
     */
    private Long usedMillSecs;

    /**
     * 离预期作废
     */
    private Long expMillSecs;

    /**
     * 每天花费
     */
    private Double costPerDay;

    /**
     * 每天花费（包含下级商品）
     */
    private Double totalCostPerDay;

    /**
     * 折旧率
     */
    private Double depRate;

    /**
     * 折旧率（包含下级商品）
     */
    private Double totalDepRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
    }

    public Double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(Double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Date getExpectDeleteDate() {
        return expectDeleteDate;
    }

    public void setExpectDeleteDate(Date expectDeleteDate) {
        this.expectDeleteDate = expectDeleteDate;
    }

    public Double getChildrenPrice() {
        return childrenPrice;
    }

    public void setChildrenPrice(Double childrenPrice) {
        this.childrenPrice = childrenPrice;
    }

    public Double getChildrenSoldPrice() {
        return childrenSoldPrice;
    }

    public void setChildrenSoldPrice(Double childrenSoldPrice) {
        this.childrenSoldPrice = childrenSoldPrice;
    }

    public Long getChildrens() {
        return childrens;
    }

    public void setChildrens(Long childrens) {
        this.childrens = childrens;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Long getUsedMillSecs() {
        return usedMillSecs;
    }

    public void setUsedMillSecs(Long usedMillSecs) {
        this.usedMillSecs = usedMillSecs;
    }

    public Long getExpMillSecs() {
        return expMillSecs;
    }

    public void setExpMillSecs(Long expMillSecs) {
        this.expMillSecs = expMillSecs;
    }

    public Double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(Double costPerDay) {
        this.costPerDay = costPerDay;
    }

    public Double getTotalCostPerDay() {
        return totalCostPerDay;
    }

    public void setTotalCostPerDay(Double totalCostPerDay) {
        this.totalCostPerDay = totalCostPerDay;
    }

    public Double getDepRate() {
        return depRate;
    }

    public void setDepRate(Double depRate) {
        this.depRate = depRate;
    }

    public Double getTotalDepRate() {
        return totalDepRate;
    }

    public void setTotalDepRate(Double totalDepRate) {
        this.totalDepRate = totalDepRate;
    }
}
