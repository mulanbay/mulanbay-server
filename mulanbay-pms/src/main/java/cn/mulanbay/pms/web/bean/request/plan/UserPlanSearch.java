package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.PlanReportDataStatFilterType;
import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.web.bean.request.PageSearch;

public class UserPlanSearch extends PageSearch implements BindUser {


    @Query(fieldName = "id", op = Parameter.Operator.EQ)
    private Long userPlanId;

    @Query(fieldName = "planConfig.id", op = Parameter.Operator.EQ)
    private Long planConfigId;

    @Query(fieldName = "planConfig.planType", op = Parameter.Operator.EQ)
    private PlanType planType;

    @Query(fieldName = "title", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private CommonStatus status;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "planConfig.relatedBeans", op = Parameter.Operator.EQ)
    private String relatedBeans;

    private Boolean statNow;

    /**
     * 是否要预测
     */
    private Boolean predict = false;

    private PlanReportDataStatFilterType filterType;

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public Long getPlanConfigId() {
        return planConfigId;
    }

    public void setPlanConfigId(Long planConfigId) {
        this.planConfigId = planConfigId;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PlanReportDataStatFilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(PlanReportDataStatFilterType filterType) {
        this.filterType = filterType;
    }

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }

    public Boolean getStatNow() {
        return statNow;
    }

    public void setStatNow(Boolean statNow) {
        this.statNow = statNow;
    }

    public Boolean getPredict() {
        return predict;
    }

    public void setPredict(Boolean predict) {
        this.predict = predict;
    }
}
