package cn.mulanbay.pms.handler.bean;

import cn.mulanbay.pms.persistent.enums.GoodsConsumeType;
import cn.mulanbay.pms.persistent.enums.Payment;

import java.io.Serializable;
import java.util.List;

/**
 * 消费记录匹配
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BuyRecordMatchBean implements Serializable {

    private static final long serialVersionUID = 5066451654516299863L;

    private String goodsName;

    private Integer goodsTypeId;

    private String goodsTypeName;

    private Integer subGoodsTypeId;

    private String subGoodsTypeName;

    private Integer buyTypeId;

    private String shopName;

    private String brand;
    // 单价（单位：元）
    private Double price;
    //数量
    private Integer amount;
    //运费单价（单位：元）
    private Double shipment;
    // 总价（单位：元）
    private Double totalPrice;
    //支付方式
    private Payment payment;
    // 是否二手
    private Boolean secondhand;
    // 是否加入统计（比如二手的卖给别人的可以不用统计）
    private Boolean statable;
    private GoodsConsumeType consumeType;
    private String skuInfo;
    //匹配度
    private float match=0;

    //参与比较的消费记录
    private Long compareId;

    private List<String> keywords;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public Integer getSubGoodsTypeId() {
        return subGoodsTypeId;
    }

    public void setSubGoodsTypeId(Integer subGoodsTypeId) {
        this.subGoodsTypeId = subGoodsTypeId;
    }

    public String getSubGoodsTypeName() {
        return subGoodsTypeName;
    }

    public void setSubGoodsTypeName(String subGoodsTypeName) {
        this.subGoodsTypeName = subGoodsTypeName;
    }

    public Integer getBuyTypeId() {
        return buyTypeId;
    }

    public void setBuyTypeId(Integer buyTypeId) {
        this.buyTypeId = buyTypeId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getShipment() {
        return shipment;
    }

    public void setShipment(Double shipment) {
        this.shipment = shipment;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Boolean getSecondhand() {
        return secondhand;
    }

    public void setSecondhand(Boolean secondhand) {
        this.secondhand = secondhand;
    }

    public Boolean getStatable() {
        return statable;
    }

    public void setStatable(Boolean statable) {
        this.statable = statable;
    }

    public GoodsConsumeType getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(GoodsConsumeType consumeType) {
        this.consumeType = consumeType;
    }

    public String getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(String skuInfo) {
        this.skuInfo = skuInfo;
    }

    public float getMatch() {
        return match;
    }

    public void setMatch(float match) {
        this.match = match;
    }

    public Long getCompareId() {
        return compareId;
    }

    public void setCompareId(Long compareId) {
        this.compareId = compareId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
