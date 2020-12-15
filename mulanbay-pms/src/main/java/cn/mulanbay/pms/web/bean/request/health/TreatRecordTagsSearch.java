package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter.Operator;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class TreatRecordTagsSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "treat_date", op = Operator.GTE)
    private Date startDate;

    @Query(fieldName = "treat_date", op = Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Operator.EQ)
    private Long userId;

    private Boolean needRoot;

    private Integer days;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
