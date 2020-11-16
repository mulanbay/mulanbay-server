package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.web.bean.request.BaseYoyStatSearch;

public class BuyRecordYoyStatSearch extends BaseYoyStatSearch implements BindUser {

    private Boolean secondhand;

    private Double startTotalPrice;

    private Double endTotalPrice;

    private Integer goodsType;

    private Integer buyType;

    private Integer subGoodsType;

    private boolean useStatable;

    private Short consumeType;

    private Long userId;

    public Boolean getSecondhand() {
        return secondhand;
    }

    public void setSecondhand(Boolean secondhand) {
        this.secondhand = secondhand;
    }

    public Double getStartTotalPrice() {
        return startTotalPrice;
    }

    public void setStartTotalPrice(Double startTotalPrice) {
        this.startTotalPrice = startTotalPrice;
    }

    public Double getEndTotalPrice() {
        return endTotalPrice;
    }

    public void setEndTotalPrice(Double endTotalPrice) {
        this.endTotalPrice = endTotalPrice;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Integer getSubGoodsType() {
        return subGoodsType;
    }

    public void setSubGoodsType(Integer subGoodsType) {
        this.subGoodsType = subGoodsType;
    }

    public boolean isUseStatable() {
        return useStatable;
    }

    public void setUseStatable(boolean useStatable) {
        this.useStatable = useStatable;
    }

    public Short getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(Short consumeType) {
        this.consumeType = consumeType;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
