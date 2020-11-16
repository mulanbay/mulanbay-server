package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.PlanReportDataStatType;
import cn.mulanbay.pms.persistent.enums.PlanStatResultType;
import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;
import java.util.List;

public class PlanReportSearch extends PageSearch implements DateStatSearch, BindUser, FullEndDateTime {

    @Query(fieldName = "bussStatDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "bussStatDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userPlan.id", op = Parameter.Operator.EQ)
    private Long userPlanId;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "reportCountValue,reportValue", crossType = CrossType.OR, op = Parameter.Operator.GT)
    private Long minValue;

    @Query(fieldName = "userPlan.planConfig.planType", op = Parameter.Operator.EQ)
    private PlanType planType;

    @Query(fieldName = "userPlan.planConfig.relatedBeans", op = Parameter.Operator.EQ)
    private String relatedBeans;

    @Query(fieldName = "countValueStatResult", op = Parameter.Operator.IN)
    private List<PlanStatResultType> countValueStatResults;

    @Query(fieldName = "valueStatResult", op = Parameter.Operator.IN)
    private List<PlanStatResultType> valueStatResults;

    private String sortField;

    private String sortType;

    private PlanReportDataStatType dataStatType;

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

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }

    public List<PlanStatResultType> getCountValueStatResults() {
        return countValueStatResults;
    }

    public void setCountValueStatResults(List<PlanStatResultType> countValueStatResults) {
        this.countValueStatResults = countValueStatResults;
    }

    public List<PlanStatResultType> getValueStatResults() {
        return valueStatResults;
    }

    public void setValueStatResults(List<PlanStatResultType> valueStatResults) {
        this.valueStatResults = valueStatResults;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public PlanReportDataStatType getDataStatType() {
        return dataStatType;
    }

    public void setDataStatType(PlanReportDataStatType dataStatType) {
        this.dataStatType = dataStatType;
    }

}
