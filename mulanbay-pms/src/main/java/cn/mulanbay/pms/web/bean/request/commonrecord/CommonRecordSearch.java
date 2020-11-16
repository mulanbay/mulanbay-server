package cn.mulanbay.pms.web.bean.request.commonrecord;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class CommonRecordSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "commonRecordType.id", op = Parameter.Operator.EQ)
    private Integer commonRecordTypeId;

    @Query(fieldName = "occurTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occurTime", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
