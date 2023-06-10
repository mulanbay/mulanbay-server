package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.GoodsConsumeType;
import cn.mulanbay.pms.persistent.enums.Payment;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 消费记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "buy_record")
public class BuyRecord implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4411942746206104324L;
    private Long id;
    private BuyType buyType;
    private GoodsType goodsType;
    //子分类，可以为空
    private GoodsType subGoodsType;
    private Long userId;
    private String goodsName;
    //店铺名称
    private String shopName;
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
    // 购买日期
    private Date buyDate;
    // 消费日期(比如音乐会门票购买日期和实际消费日期不一样)
    private Date consumeDate;
    // 售出价格（单位：元）
    private Double soldPrice;
    // 作废日期
    private Date deleteDate;
    // 预期作废日期
    private Date expectDeleteDate;
    // 状态
    private Status status;
    //关键字，统计使用
    private String keywords;
    //品牌
    private String brand;
    private String remark;
    // 是否二手
    private Boolean secondhand;
    // 是否加入统计（比如二手的卖给别人的可以不用统计）
    private Boolean statable;
    private GoodsConsumeType consumeType;
    private String skuInfo;
    //父级ID
    private Long pid;
    //看病记录
    private Long treatRecordId;
    private Date createdTime;
    private Date lastModifyTime;

    // Constructors

    /**
     * default constructor
     */
    public BuyRecord() {
    }


    public BuyRecord(String goodsName, String shopName, String keywords, String brand, String skuInfo) {
        this.goodsName = goodsName;
        this.shopName = shopName;
        this.keywords = keywords;
        this.brand = brand;
        this.skuInfo = skuInfo;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "buy_type_id", nullable = false)
    public BuyType getBuyType() {
        return this.buyType;
    }

    public void setBuyType(BuyType buyType) {
        this.buyType = buyType;
    }

    @ManyToOne
    @JoinColumn(name = "goods_type_id")
    public GoodsType getGoodsType() {
        return this.goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    @ManyToOne
    @JoinColumn(name = "sub_goods_type_id", nullable = true)
    public GoodsType getSubGoodsType() {
        return subGoodsType;
    }

    public void setSubGoodsType(GoodsType subGoodsType) {
        this.subGoodsType = subGoodsType;
    }

    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "goods_name", nullable = false, length = 200)
    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Column(name = "shop_name", nullable = false, length = 200)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "amount", nullable = false)
    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Column(name = "shipment", nullable = false)
    public Double getShipment() {
        return this.shipment;
    }

    public void setShipment(Double shipment) {
        this.shipment = shipment;
    }

    @Column(name = "total_price", nullable = false)
    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "payment")
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Column(name = "buy_date")
    public Date getBuyDate() {
        return this.buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    @Column(name = "consume_date")
    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
    }

    @Column(name = "sold_price")
    public Double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(Double soldPrice) {
        this.soldPrice = soldPrice;
    }

    @Column(name = "delete_date")
    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    @Column(name = "expect_delete_date")
    public Date getExpectDeleteDate() {
        return expectDeleteDate;
    }

    public void setExpectDeleteDate(Date expectDeleteDate) {
        this.expectDeleteDate = expectDeleteDate;
    }

    @Column(name = "status", nullable = false)
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "keywords", length = 64)
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column(name = "brand", length = 64)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "secondhand")
    public Boolean getSecondhand() {
        return secondhand;
    }

    public void setSecondhand(Boolean secondhand) {
        this.secondhand = secondhand;
    }

    @Column(name = "statable")
    public Boolean getStatable() {
        return statable;
    }

    public void setStatable(Boolean statable) {
        this.statable = statable;
    }

    @Column(name = "consume_type")
    public GoodsConsumeType getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(GoodsConsumeType consumeType) {
        this.consumeType = consumeType;
    }

    @Column(name = "sku_info")
    public String getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(String skuInfo) {
        this.skuInfo = skuInfo;
    }

    @Column(name = "pid")
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Column(name = "treat_record_id")
    public Long getTreatRecordId() {
        return treatRecordId;
    }

    public void setTreatRecordId(Long treatRecordId) {
        this.treatRecordId = treatRecordId;
    }

    @Column(name = "created_time", length = 19)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "last_modify_time", length = 19)
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Transient
    public String getConsumeTypeName() {
        return consumeType.getName();
    }

    @Transient
    public String getStatusName() {
        return status.getName();
    }

    @Transient
    public String getPaymentName() {
        return payment == null ? null : payment.getName();
    }

    /**
     * 目前enum的映射采用默认的orinal（序列号），因此类中所列的枚举值不能改变顺序
     */
    public enum Status {
        UNBUY(0, "未购买"),
        BUY(1, "已购买");
        private int value;
        private String name;

        Status(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
