package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.PlanReportDataStatFilterType;
import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.web.bean.request.PageSearch;

public class PlanConfigSearch extends PageSearch {

    @Query(fieldName = "id", op = Parameter.Operator.EQ)
    private Long planConfigId;

    @Query(fieldName = "planType", op = Parameter.Operator.EQ)
    private PlanType planType;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private CommonStatus status;

    @Query(fieldName = "relatedBeans", op = Parameter.Operator.EQ)
    private String relatedBeans;

    @Query(fieldName = "level", op = Parameter.Operator.LTE)
    public Integer level;

    private PlanReportDataStatFilterType filterType;

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

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }

    public PlanReportDataStatFilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(PlanReportDataStatFilterType filterType) {
        this.filterType = filterType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
