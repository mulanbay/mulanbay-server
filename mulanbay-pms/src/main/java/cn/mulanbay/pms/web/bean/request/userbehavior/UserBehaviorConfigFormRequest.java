package cn.mulanbay.pms.web.bean.request.userbehavior;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.SqlType;
import cn.mulanbay.pms.persistent.enums.UserBehaviorType;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserBehaviorConfigFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.userBehaviorConfig.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{validate.userBehaviorConfig.title.notEmpty}")
    private String title;

    @NotNull(message = "{validate.userBehaviorConfig.behaviorType.NotNull}")
    private UserBehaviorType behaviorType;

    @NotNull(message = "{validate.userBehaviorConfig.sqlType.NotNull}")
    private SqlType sqlType;

    @NotEmpty(message = "{validate.userBehaviorConfig.sqlContent.notEmpty}")
    private String sqlContent;
    //时间字段，时间过滤使用
    @NotEmpty(message = "{validate.userBehaviorConfig.dateField.notEmpty}")
    private String dateField;
    //用户绑定使用
    @NotEmpty(message = "{validate.userBehaviorConfig.userField.notEmpty}")
    private String userField;
    //关键字，可以做基于名称的搜索，跨多个域以英文逗号分隔
    private String keywords;
    //关键字查询语句
    private String keywordsSearchSql;
    //日期区段，比如旅行虽然只有开始日期，其实是跨好几天时间，根据统计出的天数加时间
    @NotNull(message = "{validate.userBehaviorConfig.dateRegion.NotNull}")
    private Boolean dateRegion;

    @NotEmpty(message = "{validate.userBehaviorConfig.unit.notEmpty}")
    private String unit;

    @NotNull(message = "{validate.userBehaviorConfig.status.NotNull}")
    private CommonStatus status;
    //加入月分析
    //@NotNull(message = "{validate.userBehaviorConfig.monthStat.NotNull}")
    private Boolean monthStat;
    // 级别，不同的等级可以看到不同的东西

    @NotNull(message = "{validate.userBehaviorConfig.level.NotNull}")
    private Integer level;

    @NotNull(message = "{validate.userBehaviorConfig.orderIndex.NotNull}")
    private Short orderIndex;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserBehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(UserBehaviorType behaviorType) {
        this.behaviorType = behaviorType;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywordsSearchSql() {
        return keywordsSearchSql;
    }

    public void setKeywordsSearchSql(String keywordsSearchSql) {
        this.keywordsSearchSql = keywordsSearchSql;
    }

    public Boolean getDateRegion() {
        return dateRegion;
    }

    public void setDateRegion(Boolean dateRegion) {
        this.dateRegion = dateRegion;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Boolean getMonthStat() {
        return monthStat;
    }

    public void setMonthStat(Boolean monthStat) {
        this.monthStat = monthStat;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
