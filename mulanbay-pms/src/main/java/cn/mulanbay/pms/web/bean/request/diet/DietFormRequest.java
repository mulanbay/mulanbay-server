package cn.mulanbay.pms.web.bean.request.diet;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.DietSource;
import cn.mulanbay.pms.persistent.enums.DietType;
import cn.mulanbay.pms.persistent.enums.FoodType;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DietFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotNull(message = "{validate.diet.dietType.NotNull}")
    private DietType dietType;

    @NotNull(message = "{validate.diet.dietSource.NotNull}")
    private DietSource dietSource;

    @NotNull(message = "{validate.diet.foods.NotNull}")
    private String foods;

    @NotNull(message = "{validate.diet.foodType.NotNull}")
    private FoodType foodType;

    @NotNull(message = "{validate.diet.occurTime.NotNull}")
    private Date occurTime;

    private String shop;

    private String location;

    private String tags;

    @NotNull(message = "{validate.diet.price.NotNull}")
    private Double price;

    private Integer score;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public DietSource getDietSource() {
        return dietSource;
    }

    public void setDietSource(DietSource dietSource) {
        this.dietSource = dietSource;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
