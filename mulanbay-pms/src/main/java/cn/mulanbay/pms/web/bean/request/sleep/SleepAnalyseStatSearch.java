package cn.mulanbay.pms.web.bean.request.sleep;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.SleepStatType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class SleepAnalyseStatSearch extends QueryBuilder implements DateStatSearch, BindUser {


    @Query(fieldName = "sleep_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "sleep_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    private Long userId;

    private DateGroupType xgroupType;

    private SleepStatType ygroupType;

    public DateGroupType getXgroupType() {
        return xgroupType;
    }

    public void setXgroupType(DateGroupType xgroupType) {
        this.xgroupType = xgroupType;
    }

    public SleepStatType getYgroupType() {
        return ygroupType;
    }

    public void setYgroupType(SleepStatType ygroupType) {
        this.ygroupType = ygroupType;
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

}
