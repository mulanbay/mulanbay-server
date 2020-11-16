package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.TreatTestResult;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TreatTestFormRequest implements BindUser {

    private Long id;

    @NotNull(message = "{validate.treatTest.treatOperationId.NotNull}")
    private Long treatOperationId;

    private Long userId;

    @NotEmpty(message = "{validate.treatTest.name.NotEmpty}")
    private String name;

    private String unit;

    @NotEmpty(message = "{validate.treatTest.value.NotEmpty}")
    private String value;

    private Double minValue;

    private Double maxValue;

    private String referScope;

    @NotNull(message = "{validate.treatTest.testDate.NotNull}")
    private Date testDate;

    //空为自动计算
    private TreatTestResult result;

    private String typeName;

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

    public Long getTreatOperationId() {
        return treatOperationId;
    }

    public void setTreatOperationId(Long treatOperationId) {
        this.treatOperationId = treatOperationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public String getReferScope() {
        return referScope;
    }

    public void setReferScope(String referScope) {
        this.referScope = referScope;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public TreatTestResult getResult() {
        return result;
    }

    public void setResult(TreatTestResult result) {
        this.result = result;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
