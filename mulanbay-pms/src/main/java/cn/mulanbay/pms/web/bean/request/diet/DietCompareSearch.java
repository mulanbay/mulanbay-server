package cn.mulanbay.pms.web.bean.request.diet;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class DietCompareSearch extends QueryBuilder implements BindUser, DateStatSearch {

    @Query(fieldName = "occur_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "location", op = Parameter.Operator.EQ)
    private String location;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private StatField statField;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public StatField getStatField() {
        return statField;
    }

    public void setStatField(StatField statField) {
        this.statField = statField;
    }

    /**
     * 统计字段
     */
    public enum StatField {
        COUNTS("次"),
        TOTAL_PRICE("元"),
        AVG_PRICE("元"),
        AVG_SCORE("元");

        private String unit;

        StatField(String unit) {
            this.unit = unit;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
