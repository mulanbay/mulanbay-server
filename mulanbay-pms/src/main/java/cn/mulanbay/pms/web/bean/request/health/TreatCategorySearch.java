package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class TreatCategorySearch extends PageSearch implements DateStatSearch, BindUser {

    @Query(fieldName = "treatDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "treatDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    //距离现在的天数
    private Integer days;

    //分组的字段，对应数据库中的字段名
    private String groupField;

    private Boolean needRoot;

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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }

}
