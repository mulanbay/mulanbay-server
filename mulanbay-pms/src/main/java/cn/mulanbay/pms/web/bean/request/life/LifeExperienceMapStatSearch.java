package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.ExperienceType;
import cn.mulanbay.pms.persistent.enums.MapType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;
import java.util.List;

public class LifeExperienceMapStatSearch extends QueryBuilder implements DateStatSearch, BindUser {

    @Query(fieldName = "days", op = Parameter.Operator.GTE)
    private Integer minDays;

    @Query(fieldName = "max_days", op = Parameter.Operator.LTE)
    private Integer maxDays;

    @Query(fieldName = "occur_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "type", op = Parameter.Operator.IN)
    private String inTypes;

    @Query(fieldName = "start_city", op = Parameter.Operator.EQ)
    private String startCity;

    private List<ExperienceType> types;

    private MapType mapType;

    private StatType statType;

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    public Integer getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
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

    public String getInTypes() {
        return inTypes;
    }

    public void setInTypes(String inTypes) {
        this.inTypes = inTypes;
    }

    public List<ExperienceType> getTypes() {
        return types;
    }

    public void setTypes(List<ExperienceType> types) {
        this.types = types;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public StatType getStatType() {
        return statType;
    }

    public void setStatType(StatType statType) {
        this.statType = statType;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public void setIntTypes(List<ExperienceType> types) {
        if (types != null && !types.isEmpty()) {
            int n = types.size();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < n; i++) {
                sb.append(types.get(i).getValue());
                if (i < n - 1) {
                    sb.append(",");
                }
            }
            this.inTypes = sb.toString();
        }
    }

    public enum StatType {
        COUNT("次数"), DAYS("天数"), COST("花费");

        StatType(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
