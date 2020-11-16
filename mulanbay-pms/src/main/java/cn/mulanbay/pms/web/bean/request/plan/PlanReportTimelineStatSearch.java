package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.DateGroupType;

import javax.validation.constraints.NotNull;

public class PlanReportTimelineStatSearch implements BindUser {

    @NotNull(message = "{validate.stat.year.NotNull}")
    private Integer year;

    @NotNull(message = "{validate.stat.month.NotNull}")
    private Integer month;

    public Long userId;

    @NotNull(message = "{validate.planReport.userPlanId.NotNull}")
    private Long userPlanId;

    private DateGroupType dateGroupType;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }
}
