package cn.mulanbay.pms.web.bean.request.diet;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.DietSource;
import cn.mulanbay.pms.persistent.enums.DietType;
import cn.mulanbay.pms.persistent.enums.FoodType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class DietPriceAnalyseSearch extends QueryBuilder implements BindUser, DateStatSearch {

    @Query(fieldName = "occur_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "foods", op = Parameter.Operator.LIKE)
    private String name;

    private DietType dietType;

    @Query(fieldName = "diet_type", op = Parameter.Operator.EQ)
    private Integer dietTypeInt;

    private DietSource dietSource;

    @Query(fieldName = "diet_source", op = Parameter.Operator.EQ)
    private Integer dietSourceInt;

    @Query(fieldName = "price", op = Parameter.Operator.GT)
    private Double minPrice;

    private FoodType foodType;

    /**
     * 由于后端采用的是sql查询，枚举类型的参数绑定有些问题，需要转换为int
     */
    @Query(fieldName = "food_type", op = Parameter.Operator.EQ)
    private Integer foodTypeInt;

    @Query(fieldName = "location", op = Parameter.Operator.EQ)
    private String location;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private String dateGroupTypeStr;

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
        return DateGroupType.getDateGroupType(dateGroupTypeStr);
    }

    public String getDateGroupTypeStr() {
        return dateGroupTypeStr;
    }

    public void setDateGroupTypeStr(String dateGroupTypeStr) {
        this.dateGroupTypeStr = dateGroupTypeStr;
    }

    @Override
    public Boolean isCompliteDate() {
        return null;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
        if (dietType != null) {
            this.dietTypeInt = dietType.ordinal();
        }
    }

    public DietSource getDietSource() {
        return dietSource;
    }

    public void setDietSource(DietSource dietSource) {
        this.dietSource = dietSource;
        if (dietSource != null) {
            this.dietSourceInt = dietSource.ordinal();
        }
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
        if (foodType != null) {
            this.foodTypeInt = foodType.ordinal();
        }
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

    public Integer getDietTypeInt() {
        return dietTypeInt;
    }

    public void setDietTypeInt(Integer dietTypeInt) {
        this.dietTypeInt = dietTypeInt;
    }

    public Integer getDietSourceInt() {
        return dietSourceInt;
    }

    public void setDietSourceInt(Integer dietSourceInt) {
        this.dietSourceInt = dietSourceInt;
    }

    public Integer getFoodTypeInt() {
        return foodTypeInt;
    }

    public void setFoodTypeInt(Integer foodTypeInt) {
        this.foodTypeInt = foodTypeInt;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }
}
