package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.NullType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Parameter.Operator;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class BuyRecordUseTimeListSearch extends PageSearch implements DateStatSearch, BindUser, FullEndDateTime {

    @Query(fieldName = "goods_name,keywords,shop_name,remark", op = Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "buy_date", op = Operator.GTE)
    private Date startDate;

    @Query(fieldName = "buy_date", op = Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Operator.EQ)
    private Long userId;

    @Query(fieldName = "goods_type_id", op = Operator.EQ)
    private Integer goodsType;

    @Query(fieldName = "sub_goods_type_id", op = Operator.EQ)
    private Integer subGoodsType;

    @Query(fieldName = "buy_type_id", op = Operator.EQ)
    private Integer buyType;

    @Query(fieldName = "secondhand", op = Operator.EQ)
    private Boolean secondhand;

    /**
     * 非空表示设置过作废日期
     */
    @Query(fieldName = "deleteDate", op = Parameter.Operator.NULL_NOTNULL)
    private NullType deleteDateType;

    private String sortField;

    private String sortType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
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
    public DateGroupType getDateGroupType() {
        return null;
    }

    @Override
    public Boolean isCompliteDate() {
        return null;
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

    public Boolean getSecondhand() {
        return secondhand;
    }

    public void setSecondhand(Boolean secondhand) {
        this.secondhand = secondhand;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public NullType getDeleteDateType() {
        return deleteDateType;
    }

    public void setDeleteDateType(NullType deleteDateType) {
        this.deleteDateType = deleteDateType;
    }

}
