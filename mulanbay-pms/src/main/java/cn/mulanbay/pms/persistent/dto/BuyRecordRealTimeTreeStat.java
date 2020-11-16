package cn.mulanbay.pms.persistent.dto;

public class BuyRecordRealTimeTreeStat {

    private Integer goodsId;
    private String goodsName;
    private Integer subGoodsId;
    private String subGoodsName;
    private double value;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSubGoodsId() {
        return subGoodsId;
    }

    public void setSubGoodsId(Integer subGoodsId) {
        this.subGoodsId = subGoodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSubGoodsName() {
        return subGoodsName;
    }

    public void setSubGoodsName(String subGoodsName) {
        this.subGoodsName = subGoodsName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
