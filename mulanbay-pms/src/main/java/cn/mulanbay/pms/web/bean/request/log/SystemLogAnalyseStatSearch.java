package cn.mulanbay.pms.web.bean.request.log;

import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter.Operator;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;

import java.util.Date;

public class SystemLogAnalyseStatSearch extends QueryBuilder implements FullEndDateTime {

    private String username;

    private String groupField;

    @Query(fieldName = "title,content", op = Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "occur_time", op = Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occur_time", op = Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Operator.EQ)
    private Long userId;

    @Query(fieldName = "log_level", op = Operator.EQ)
    private Short logLevel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Short logLevel) {
        this.logLevel = logLevel;
    }
}
