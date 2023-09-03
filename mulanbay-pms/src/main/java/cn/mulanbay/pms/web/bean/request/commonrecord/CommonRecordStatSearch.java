package cn.mulanbay.pms.web.bean.request.commonrecord;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CommonRecordStatSearch extends QueryBuilder implements BindUser, FullEndDateTime {

    @NotNull(message = "{validate.commonRecord.commonRecordTypeId.NotNull}")
    @Query(fieldName = "common_record_type_id", op = Parameter.Operator.EQ)
    private Integer commonRecordTypeId;

    @Query(fieldName = "name", op = Parameter.Operator.EQ)
    private String name;

    @Query(fieldName = "occur_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    public Integer getCommonRecordTypeId() {
        return commonRecordTypeId;
    }

    public void setCommonRecordTypeId(Integer commonRecordTypeId) {
        this.commonRecordTypeId = commonRecordTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
