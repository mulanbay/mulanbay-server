package cn.mulanbay.pms.web.bean.request.diet;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.DietType;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class DietAvgVarietyLogSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "endDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "endDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "dietType", op = Parameter.Operator.EQ, supportNullValue = true)
    private DietType dietType;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "location", op = Parameter.Operator.EQ)
    private String location;

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

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
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
}
