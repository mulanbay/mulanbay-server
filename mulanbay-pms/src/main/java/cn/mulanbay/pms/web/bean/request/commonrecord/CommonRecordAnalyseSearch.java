package cn.mulanbay.pms.web.bean.request.commonrecord;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.ValueType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CommonRecordAnalyseSearch extends QueryBuilder implements BindUser, FullEndDateTime {

    @NotNull(message = "{validate.commonRecord.commonRecordTypeId.NotNull}")
    @Query(fieldName = "common_record_type_id", op = Parameter.Operator.EQ)
    private Integer commonRecordTypeId;

    @Query(fieldName = "occur_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private String groupField;

    private ValueType valueType;

    public Integer getCommonRecordTypeId() {
        return commonRecordTypeId;
    }

    public void setCommonRecordTypeId(Integer commonRecordTypeId) {
        this.commonRecordTypeId = commonRecordTypeId;
    }

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
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }
}
