package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DataInputAnalyseFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.dataInputAnalyse.name.NotEmpty}")
    private String name;

    @NotEmpty(message = "{validate.dataInputAnalyse.tableName.NotEmpty}")
    private String tableName;

    //业务字段
    @NotEmpty(message = "{validate.dataInputAnalyse.bussField.NotEmpty}")
    private String bussField;

    //表示数据录入的字段
    @NotEmpty(message = "{validate.dataInputAnalyse.inputField.NotEmpty}")
    private String inputField;

    @NotEmpty(message = "{validate.dataInputAnalyse.userField.NotEmpty}")
    private String userField;

    @NotNull(message = "{validate.diary.status.NotNull}")
    private CommonStatus status;

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBussField() {
        return bussField;
    }

    public void setBussField(String bussField) {
        this.bussField = bussField;
    }

    public String getInputField() {
        return inputField;
    }

    public void setInputField(String inputField) {
        this.inputField = inputField;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
