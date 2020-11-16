package cn.mulanbay.pms.web.bean.request.bodyabnormal;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.BodyAbnormalRecordGroupType;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class BodyAbnormalRecordStatSearch extends QueryBuilder implements DateStatSearch, BindUser, FullEndDateTime {

    @Query(fieldName = "occur_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    private Long userId;

    private BodyAbnormalRecordGroupType groupField;

    @NotNull(message = "{validate.stat.chartType.notNull}")
    private ChartType chartType;

    //检索使用
    private String name;

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
        return null;
    }

    @Override
    public Boolean isCompliteDate() {
        return null;
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

    public BodyAbnormalRecordGroupType getGroupField() {
        return groupField;
    }

    public void setGroupField(BodyAbnormalRecordGroupType groupField) {
        this.groupField = groupField;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
