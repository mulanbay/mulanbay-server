package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.CompareType;
import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.pms.persistent.enums.SqlType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PlanConfigFormRequest implements BindUser {

    private Long id;
    private Long userId;

    private Long tmpPlanConfigId;

    @NotEmpty(message = "{validate.planConfig.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{validate.planConfig.title.notEmpty}")
    private String title;

    @NotNull(message = "{validate.planConfig.planType.NotNull}")
    private PlanType planType;

    @NotNull(message = "{validate.planConfig.sqlType.NotNull}")
    private SqlType sqlType;

    @NotEmpty(message = "{validate.planConfig.sqlContent.NotEmpty}")
    private String sqlContent;

    //时间字段，时间过滤使用
    @NotEmpty(message = "{validate.planConfig.dateField.NotEmpty}")
    private String dateField;

    //用户绑定使用
    @NotEmpty(message = "{validate.planConfig.userField.NotEmpty}")
    private String userField;

    @NotEmpty(message = "{validate.planConfig.unit.NotEmpty}")
    private String unit;

    @NotNull(message = "{validate.planConfig.compareType.NotNull}")
    private CompareType compareType;

    @NotNull(message = "{validate.planConfig.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.planConfig.orderIndex.NotNull}")
    private Short orderIndex;
    //关联的bean，可以根据这个在各个其他界面来查询本业务管理的计划
    private String relatedBeans;
    //次数值
    @NotNull(message = "{validate.planConfig.defaultPlanCountValue.NotNull}")
    private Long defaultPlanCountValue;
    //值，比如购买金额，锻炼时间

    @NotNull(message = "{validate.planConfig.defaultPlanValue.NotNull}")
    private Long defaultPlanValue;
    //等级
    @NotNull(message = "{validate.planConfig.level.NotNull}")
    private Integer level;

    //奖励积分(正为加，负为减)
    @NotNull(message = "{validate.planConfig.rewardPoint.NotNull}")
    private Integer rewardPoint;

    private String bussKey;

    private String defaultCalendarTitle;

    private String url;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTmpPlanConfigId() {
        return tmpPlanConfigId;
    }

    public void setTmpPlanConfigId(Long tmpPlanConfigId) {
        this.tmpPlanConfigId = tmpPlanConfigId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public String getDateField() {
        return dateField;
    }

    public void setDateField(String dateField) {
        this.dateField = dateField;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }

    public Long getDefaultPlanCountValue() {
        return defaultPlanCountValue;
    }

    public void setDefaultPlanCountValue(Long defaultPlanCountValue) {
        this.defaultPlanCountValue = defaultPlanCountValue;
    }

    public Long getDefaultPlanValue() {
        return defaultPlanValue;
    }

    public void setDefaultPlanValue(Long defaultPlanValue) {
        this.defaultPlanValue = defaultPlanValue;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getBussKey() {
        return bussKey;
    }

    public void setBussKey(String bussKey) {
        this.bussKey = bussKey;
    }

    public String getDefaultCalendarTitle() {
        return defaultCalendarTitle;
    }

    public void setDefaultCalendarTitle(String defaultCalendarTitle) {
        this.defaultCalendarTitle = defaultCalendarTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
