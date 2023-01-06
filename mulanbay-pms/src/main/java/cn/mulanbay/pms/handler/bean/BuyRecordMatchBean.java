package cn.mulanbay.pms.handler.bean;

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

    //匹配度
    private float match=0;

    //参与比较的消费记录
    private Long compareId;

    private String shopName;

    private String brand;

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
