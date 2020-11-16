package cn.mulanbay.pms.web.bean.request.read;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class ReadingRecordStatSearch extends PageSearch implements BindUser, FullEndDateTime {

    private String dateQueryType;

    @Query(fieldName = "finished_date", op = Parameter.Operator.GTE, referFieldName = "dateQueryType")
    private Date startDate;

    @Query(fieldName = "finished_date", op = Parameter.Operator.LTE, referFieldName = "dateQueryType")
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    //因为是sql查询
    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private Integer status;

    private ReadingRecordAnalyseStatSearch.GroupType groupType;

    public String getDateQueryType() {
        return dateQueryType;
    }

    public void setDateQueryType(String dateQueryType) {
        this.dateQueryType = dateQueryType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ReadingRecordAnalyseStatSearch.GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(ReadingRecordAnalyseStatSearch.GroupType groupType) {
        this.groupType = groupType;
    }
}
