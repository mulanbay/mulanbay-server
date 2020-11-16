package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class LifeExperienceSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "country", op = Parameter.Operator.EQ)
    private String country;

    @Query(fieldName = "province", op = Parameter.Operator.EQ)
    private String province;

    @Query(fieldName = "city", op = Parameter.Operator.EQ)
    private String city;

    @Query(fieldName = "location", op = Parameter.Operator.LIKE)
    private String location;

    @Query(fieldName = "days", op = Parameter.Operator.GTE)
    private Integer minDays;

    @Query(fieldName = "maxDays", op = Parameter.Operator.LTE)
    private Date maxDays;

    @Query(fieldName = "startDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "startDate", op = Parameter.Operator.LTE)
    private Date endDate;


    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "type", op = Parameter.Operator.IN)
    private String types;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    public Date getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Date maxDays) {
        this.maxDays = maxDays;
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

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
