package cn.mulanbay.pms.web.bean.request.work;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class WorkOvertimeSearch extends PageSearch implements BindUser {

    @Query(fieldName = "company.id", op = Parameter.Operator.EQ)
    private Long companyId;

    @Query(fieldName = "hours", op = Parameter.Operator.GTE)
    private Double minHours;

    @Query(fieldName = "hours", op = Parameter.Operator.LTE)
    private Double maxHours;

    @Query(fieldName = "workDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "workDate", op = Parameter.Operator.LTE)
    private Date endDate;


    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getMinHours() {
        return minHours;
    }

    public void setMinHours(Double minHours) {
        this.minHours = minHours;
    }

    public Double getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(Double maxHours) {
        this.maxHours = maxHours;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
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
