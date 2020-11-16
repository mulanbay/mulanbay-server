package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.pms.persistent.enums.CleanType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

public class DatabaseCleanFormRequest {

    private Long id;
    private String name;
    private String tableName;
    private String dateField;
    private Integer days;
    private CleanType cleanType;
    //额外条件
    private String extraCondition;
    private CommonStatus status;
    private Short orderIndex;

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

    public String getDateField() {
        return dateField;
    }

    public void setDateField(String dateField) {
        this.dateField = dateField;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public CleanType getCleanType() {
        return cleanType;
    }

    public void setCleanType(CleanType cleanType) {
        this.cleanType = cleanType;
    }

    public String getExtraCondition() {
        return extraCondition;
    }

    public void setExtraCondition(String extraCondition) {
        this.extraCondition = extraCondition;
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
}
