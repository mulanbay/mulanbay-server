package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter.Operator;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BuyRecordWordCloudSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "goodsType.id", op = Operator.EQ)
    private Integer goodsType;

    @Query(fieldName = "subGoodsType.id", op = Operator.EQ)
    private Integer subGoodsType;

    @Query(fieldName = "buyType.id", op = Operator.EQ)
    private Integer buyType;

    @Query(fieldName = "goodsName,keywords,shopName,remark", op = Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Query(fieldName = "buyDate", op = Operator.GTE)
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Query(fieldName = "buyDate", op = Operator.LTE)
    private Date endDate;

    @Query(fieldName = "secondhand", op = Operator.EQ)
    private Boolean secondhand;

    @Query(fieldName = "userId", op = Operator.EQ)
    private Long userId;

    @Query(fieldName = "keywords", op = Operator.LIKE)
    private String keywords;

    private String field;

    private int picWidth = 500;

    private int picHeight = 400;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getSecondhand() {
        return secondhand;
    }

    public void setSecondhand(Boolean secondhand) {
        this.secondhand = secondhand;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }
}
