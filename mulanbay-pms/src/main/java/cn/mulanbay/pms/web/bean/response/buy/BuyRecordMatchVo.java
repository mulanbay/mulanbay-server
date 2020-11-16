package cn.mulanbay.pms.web.bean.response.buy;

public class BuyRecordMatchVo {

    private Integer goodsTypeId;

    private String goodsTypeName;

    private Integer subGoodsTypeId;

    private String subGoodsTypeName;

    private String shopName;

    private String brand;

    private float match;

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

    public float getMatch() {
        return match;
    }

    public void setMatch(float match) {
        this.match = match;
    }
}
