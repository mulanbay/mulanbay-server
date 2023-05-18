package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.domain.BuyRecord;
import cn.mulanbay.pms.persistent.enums.GoodsConsumeType;
import cn.mulanbay.pms.persistent.enums.Payment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class BuyRecordFormRequest implements BindUser {

    private Long id;

    @NotNull(message = "{validate.buyRecord.buyTypeId.NotNull}")
    private Integer buyTypeId;

    @NotNull(message = "{validate.buyRecord.goodsTypeId.NotNull}")
    private Integer goodsTypeId;
    //子分类，可以为空
    private Integer subGoodsTypeId;

    private Long userId;

    @NotEmpty(message = "{validate.buyRecord.goodsName.notEmpty}")
    private String goodsName;
    //店铺名称
    private String shopName;

    // 单价（单位：元）
    @NotNull(message = "{validate.buyRecord.price.NotNull}")
    private Double price;

    //数量
    @NotNull(message = "{validate.buyRecord.amount.NotNull}")
    private Integer amount;

    //运费单价（单位：元）
    @NotNull(message = "{validate.buyRecord.shipment.NotNull}")
    private Double shipment;

    //支付方式
    @NotNull(message = "{validate.buyRecord.payment.NotNull}")
    private Payment payment;

    // 购买日期
    @NotNull(message = "{validate.buyRecord.buyDate.NotNull}")
    private Date buyDate;

    // 消费日期(比如音乐会门票购买日期和实际消费日期不一样)
    private Date consumeDate;

    // 售出价格（单位：元）
    private Double soldPrice;

    // 作废日期
    private Date deleteDate;

    private Date expectDeleteDate;

    // 状态
    //@NotNull(message = "{validate.buyRecord.status.NotNull}")
    private BuyRecord.Status status = BuyRecord.Status.BUY;
    //关键字，统计使用
    private String keywords;

    private String brand;

    private String remark;

    // 是否二手
    @NotNull(message = "{validate.buyRecord.secondhand.NotNull}")
    private Boolean secondhand;

    // 是否加入统计（比如二手的卖给别人的可以不用统计）
    @NotNull(message = "{validate.buyRecord.statable.NotNull}")
    private Boolean statable = true;

    @NotNull(message = "{validate.buyRecord.consumeType.NotNull}")
    private GoodsConsumeType consumeType;

    private String skuInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBuyTypeId() {
        return buyTypeId;
    }

    public void setBuyTypeId(Integer buyTypeId) {
        this.buyTypeId = buyTypeId;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Integer getSubGoodsTypeId() {
        return subGoodsTypeId;
    }

    public void setSubGoodsTypeId(Integer subGoodsTypeId) {
        this.subGoodsTypeId = subGoodsTypeId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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

    public BuyRecord.Status getStatus() {
        return status;
    }

    public void setStatus(BuyRecord.Status status) {
        this.status = status;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(String skuInfo) {
        this.skuInfo = skuInfo;
    }

    public GoodsConsumeType getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(GoodsConsumeType consumeType) {
        this.consumeType = consumeType;
    }
}
