package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.enums.UserBehaviorType;

import java.math.BigInteger;
import java.util.Date;

public class UserBehaviorDataStat {

    //格式：2017-01-01
    private Date date;

    private String name;

    private Object value;

    //todo 不同的类型比较以后可以采用数量
    private BigInteger totalCount;

    private Boolean dateRegion;

    private UserBehaviorType behaviorType;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getDateRegion() {
        return dateRegion;
    }

    public void setDateRegion(Boolean dateRegion) {
        this.dateRegion = dateRegion;
    }

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
    }
}
