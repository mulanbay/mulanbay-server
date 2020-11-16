package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter.Operator;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class BuyRecordVarietyStatSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "goods_name,keywords,shop_name,remark", op = Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "buy_date", op = Operator.GTE)
    private Date startDate;

    @Query(fieldName = "buy_date", op = Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Operator.EQ)
    private Long userId;

    @Query(fieldName = "total_price", op = Operator.GTE)
    private Double startTotalPrice;

    @Query(fieldName = "total_price", op = Operator.LTE)
    private Double endTotalPrice;

    @Query(fieldName = "goods_type_id", op = Operator.EQ)
    private Integer goodsType;

    @Query(fieldName = "sub_goods_type_id", op = Operator.EQ)
    private Integer subGoodsType;

    @Query(fieldName = "buy_type_id", op = Operator.EQ)
    private Integer buyType;

    @Query(fieldName = "keywords", op = Operator.LIKE)
    private String keywords;

    //sql需要原始的数字类型
    @Query(fieldName = "consume_type", op = Operator.EQ)
    private Short consumeType;

    private String orderByField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getSubGoodsType() {
        return subGoodsType;
    }

    public void setSubGoodsType(Integer subGoodsType) {
        this.subGoodsType = subGoodsType;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Short getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(Short consumeType) {
        this.consumeType = consumeType;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }
}
