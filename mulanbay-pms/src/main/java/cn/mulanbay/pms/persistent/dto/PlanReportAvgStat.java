package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.domain.UserPlan;
import cn.mulanbay.pms.persistent.domain.UserPlanConfigValue;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PlanReportAvgStat {

    private int id;

    private BigDecimal reportCountValue;

    private BigDecimal reportValue;

    private BigInteger userPlanId;

    private UserPlan userPlan;

    private int year;

    private UserPlanConfigValue userPlanConfigValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getReportCountValue() {
        return reportCountValue;
    }

    public void setReportCountValue(BigDecimal reportCountValue) {
        this.reportCountValue = reportCountValue;
    }

    public BigDecimal getReportValue() {
        return reportValue;
    }

    public void setReportValue(BigDecimal reportValue) {
        this.reportValue = reportValue;
    }

    public BigInteger getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(BigInteger userPlanId) {
        this.userPlanId = userPlanId;
    }

    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public UserPlanConfigValue getUserPlanConfigValue() {
        return userPlanConfigValue;
    }

    public void setUserPlanConfigValue(UserPlanConfigValue userPlanConfigValue) {
        this.userPlanConfigValue = userPlanConfigValue;
    }
}
