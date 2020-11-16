package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.ManualStatType;
import cn.mulanbay.pms.persistent.enums.PlanReportReStatCompareType;
import cn.mulanbay.pms.persistent.enums.PlanType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PlanReportManualStat implements BindUser {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Long userPlanId;

    private Long userId;

    private ManualStatType statType;

    private PlanType planType;

    private PlanReportReStatCompareType reStatCompareType;

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

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ManualStatType getStatType() {
        return statType;
    }

    public void setStatType(ManualStatType statType) {
        this.statType = statType;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public PlanReportReStatCompareType getReStatCompareType() {
        return reStatCompareType;
    }

    public void setReStatCompareType(PlanReportReStatCompareType reStatCompareType) {
        this.reStatCompareType = reStatCompareType;
    }
}
