package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class TreatRecordDateStatSearch extends QueryBuilder implements DateStatSearch, BindUser {

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "treat_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "treat_date", op = Parameter.Operator.LTE)
    private Date endDate;

    // 支持多个字段查询
    @Query(fieldName = "hospital,department,organ,disease,diagnosed_disease", op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "treat_type", op = Parameter.Operator.EQ)
    private Short treatType;

    //需要统计的费用字段
    private String feeField;

    // 是否补全日期
    private Boolean compliteDate;

    private DateGroupType dateGroupType;

    //做统计绘图跟踪使用
    private String disease;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    public Boolean getCompliteDate() {
        return compliteDate;
    }

    public void setCompliteDate(Boolean compliteDate) {
        this.compliteDate = compliteDate;
    }

    @Override
    public Boolean isCompliteDate() {
        return this.compliteDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeeField() {
        return feeField;
    }

    public void setFeeField(String feeField) {
        this.feeField = feeField;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Short getTreatType() {
        return treatType;
    }

    public void setTreatType(Short treatType) {
        this.treatType = treatType;
    }
}
