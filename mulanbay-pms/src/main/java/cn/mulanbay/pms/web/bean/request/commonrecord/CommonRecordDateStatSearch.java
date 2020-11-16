package cn.mulanbay.pms.web.bean.request.commonrecord;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class CommonRecordDateStatSearch extends QueryBuilder implements DateStatSearch, BindUser, FullEndDateTime {

    @Query(fieldName = "common_record_type_id", op = Parameter.Operator.EQ)
    private Integer commonRecordTypeId;

    // 支持多个字段查询
    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "occur_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private DateGroupType dateGroupType;

    // 是否补全日期
    private Boolean compliteDate;

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

    public Boolean getCompliteDate() {
        return compliteDate;
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

    @Override
    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    @Override
    public Boolean isCompliteDate() {
        return compliteDate;
    }

    public void setCompliteDate(Boolean compliteDate) {
        this.compliteDate = compliteDate;
    }

}
