package cn.mulanbay.pms.web.bean.request.diet;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class DietAnalyseSearch extends PageSearch implements BindUser, DateStatSearch {

    @Query(fieldName = "a.occur_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "a.occur_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "a.foods", op = Parameter.Operator.LIKE)
    private String name;

    private DietType dietType;

    @Query(fieldName = "a.diet_type", op = Parameter.Operator.EQ)
    private Integer dietTypeInt;

    private DietSource dietSource;

    @Query(fieldName = "a.diet_source", op = Parameter.Operator.EQ)
    private Integer dietSourceInt;

    @Query(fieldName = "a.score", op = Parameter.Operator.GTE)
    private Integer minScore;

    @Query(fieldName = "a.score", op = Parameter.Operator.LTE)
    private Integer maxScore;

    private FoodType foodType;

    /**
     * 由于后端采用的是sql查询，枚举类型的参数绑定有些问题，需要转换为int
     */
    @Query(fieldName = "a.food_type", op = Parameter.Operator.EQ)
    private Integer foodTypeInt;

    @Query(fieldName = "a.location", op = Parameter.Operator.EQ)
    private String location;

    @Query(fieldName = "a.user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private ChartType chartType;

    private StatField field;

    private int minCount;

    //包含未知的数据
    private boolean includeUnknown;

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

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
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

    public StatField getField() {
        return field;
    }

    public void setField(StatField field) {
        this.field = field;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public boolean isIncludeUnknown() {
        return includeUnknown;
    }

    public void setIncludeUnknown(boolean includeUnknown) {
        this.includeUnknown = includeUnknown;
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

    /**
     * 统计字段
     */
    public enum StatField {
        FOODS("foods"),
        TAGS("tags"),
        SHOP("shop"),
        CLASS_NAME("class_name"),
        TYPE("type"),
        DIET_SOURCE("diet_source"),
        FOOD_TYPE("food_type"),
        DIET_TYPE("diet_type");
        private String fieldName;

        StatField(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }
}
