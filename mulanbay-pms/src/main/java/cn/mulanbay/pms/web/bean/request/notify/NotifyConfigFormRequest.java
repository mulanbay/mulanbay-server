package cn.mulanbay.pms.web.bean.request.notify;

import cn.mulanbay.pms.persistent.enums.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NotifyConfigFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.notifyConfig.name.NotEmpty}")
    private String name;

    @NotEmpty(message = "{validate.notifyConfig.title.NotEmpty}")
    private String title;

    @NotNull(message = "{validate.notifyConfig.sqlType.NotNull}")
    private SqlType sqlType;

    @NotEmpty(message = "{validate.notifyConfig.sqlContent.NotEmpty}")
    private String sqlContent;

    @NotNull(message = "{validate.notifyConfig.resultType.NotNull}")
    private ResultType resultType;

    @NotNull(message = "{validate.notifyConfig.valueType.NotNull}")
    private ValueType valueType;

    @NotNull(message = "{validate.notifyConfig.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.notifyConfig.orderIndex.NotNull}")
    private Short orderIndex;

    @NotNull(message = "{validate.notifyConfig.notifyType.NotNull}")
    private NotifyType notifyType;

    private String userField;

    private String relatedBeans;

    @NotNull(message = "{validate.notifyConfig.level.NotNull}")
    private Integer level;
    //奖励积分(正为加，负为减)
    @NotNull(message = "{validate.notifyConfig.rewardPoint.NotNull}")
    private Integer rewardPoint;

    private String bussKey;

    private String defaultCalendarTitle;

    //链接地址
    private String url;
    //tab名称
    private String tabName;

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

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
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

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
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

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
