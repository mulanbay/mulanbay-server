package cn.mulanbay.pms.web.bean.request.read;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.GroupType;

import java.util.Date;

public class ReadingRecordOverallStatSearch extends QueryBuilder implements BindUser, FullEndDateTime {

    @Query(fieldName = "finished_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "finished_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private DateGroupType dateGroupType;

    /**
     * 是次数还是数值
     */
    private GroupType valueType;

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

    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    public GroupType getValueType() {
        return valueType;
    }

    public void setValueType(GroupType valueType) {
        this.valueType = valueType;
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
