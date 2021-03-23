package cn.mulanbay.pms.web.bean.request.diet;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class DietWordCloudSearch extends QueryBuilder implements BindUser, FullEndDateTime {

    @Query(fieldName = "occur_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "foods,shop,tags", op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "diet_type", op = Parameter.Operator.EQ)
    private Integer dietType;

    @Query(fieldName = "diet_source", op = Parameter.Operator.EQ)
    private Integer dietSource;

    @Query(fieldName = "food_type", op = Parameter.Operator.EQ)
    private Integer foodType;

    @Query(fieldName = "score", op = Parameter.Operator.GTE)
    private Integer minScore;

    @Query(fieldName = "score", op = Parameter.Operator.LTE)
    private Integer maxScore;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private String field;

    private int picWidth = 500;

    private int picHeight = 400;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDietType() {
        return dietType;
    }

    public void setDietType(Integer dietType) {
        this.dietType = dietType;
    }

    public Integer getDietSource() {
        return dietSource;
    }

    public void setDietSource(Integer dietSource) {
        this.dietSource = dietSource;
    }

    public Integer getFoodType() {
        return foodType;
    }

    public void setFoodType(Integer foodType) {
        this.foodType = foodType;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
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
