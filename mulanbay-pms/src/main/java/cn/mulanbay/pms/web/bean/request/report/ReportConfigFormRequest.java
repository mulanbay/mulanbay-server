package cn.mulanbay.pms.web.bean.request.report;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.SqlType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReportConfigFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.reportConfig.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{validate.reportConfig.title.notEmpty}")
    private String title;

    @NotNull(message = "{validate.reportConfig.sqlType.NotNull}")
    private SqlType sqlType;

    @NotEmpty(message = "{validate.reportConfig.sqlContent.notEmpty}")
    private String sqlContent;

    //@NotNull(message = "{validate.reportConfig.warningValue.NotNull}")
    private Integer warningValue;

    //@NotNull(message = "{validate.reportConfig.alertValue.NotNull}")
    private Integer alertValue;
    //返回结果的列数量，每次保存、修改时设置
    private Integer resultColumns;

    @NotNull(message = "{validate.reportConfig.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.reportConfig.orderIndex.NotNull}")
    private Short orderIndex;
    //是否和用户绑定

    @NotNull(message = "{validate.reportConfig.userBand.NotNull}")
    private Boolean userBand;

    //结果模板，格式：{0}次{1}元
    @NotEmpty(message = "{validate.reportConfig.resultTemplate.notEmpty}")
    private String resultTemplate;

    @NotNull(message = "{validate.reportConfig.level.NotNull}")
    private Integer level;

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

    public Integer getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(Integer warningValue) {
        this.warningValue = warningValue;
    }

    public Integer getAlertValue() {
        return alertValue;
    }

    public void setAlertValue(Integer alertValue) {
        this.alertValue = alertValue;
    }

    public Integer getResultColumns() {
        return resultColumns;
    }

    public void setResultColumns(Integer resultColumns) {
        this.resultColumns = resultColumns;
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

    public Boolean getUserBand() {
        return userBand;
    }

    public void setUserBand(Boolean userBand) {
        this.userBand = userBand;
    }

    public String getResultTemplate() {
        return resultTemplate;
    }

    public void setResultTemplate(String resultTemplate) {
        this.resultTemplate = resultTemplate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
