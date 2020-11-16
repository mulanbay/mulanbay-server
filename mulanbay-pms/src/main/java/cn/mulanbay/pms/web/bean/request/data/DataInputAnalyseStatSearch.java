package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class DataInputAnalyseStatSearch extends QueryBuilder implements DateStatSearch, FullEndDateTime {

    @Query(fieldName = "id", op = Parameter.Operator.EQ)
    private Long dataInputAnalyseId;

    private Date startDate;

    private Date endDate;

    private StatType statType;

    private String compareValue;

    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private CommonStatus status;

    private String username;

    private Long userId;

    public Long getDataInputAnalyseId() {
        return dataInputAnalyseId;
    }

    public void setDataInputAnalyseId(Long dataInputAnalyseId) {
        this.dataInputAnalyseId = dataInputAnalyseId;
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

    public StatType getStatType() {
        return statType;
    }

    public void setStatType(StatType statType) {
        this.statType = statType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    public enum StatType {
        DAY, TABLE;
    }
}
