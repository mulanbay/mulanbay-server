package cn.mulanbay.pms.web.bean.request.diet;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class DietVarietySearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "occur_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "foods,shop,tags", op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "diet_type", op = Parameter.Operator.EQ)
    private Short dietType;

    @Query(fieldName = "diet_source", op = Parameter.Operator.EQ)
    private Short dietSource;

    @Query(fieldName = "food_type", op = Parameter.Operator.EQ)
    private Short foodType;

    @Query(fieldName = "score", op = Parameter.Operator.GTE)
    private Integer minScore;

    @Query(fieldName = "score", op = Parameter.Operator.LTE)
    private Integer maxScore;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "location", op = Parameter.Operator.EQ)
    private String location;

    private String orderByField;

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

    public Short getDietType() {
        return dietType;
    }

    public void setDietType(Short dietType) {
        this.dietType = dietType;
    }

    public Short getDietSource() {
        return dietSource;
    }

    public void setDietSource(Short dietSource) {
        this.dietSource = dietSource;
    }

    public Short getFoodType() {
        return foodType;
    }

    public void setFoodType(Short foodType) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }
}
