package cn.mulanbay.pms.web.bean.request.usercalendar;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserCalendarListSearch extends QueryBuilder implements BindUser, FullEndDateTime {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    private Date endDate;

    private String name;

    public Long userId;

    private Boolean needFinished;

    private Boolean needPeriod;

    private Boolean needBudget;

    private Boolean needTreatDrug;

    private Boolean needBandLog;

    private UserCalendarSource sourceType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getNeedFinished() {
        return needFinished;
    }

    public void setNeedFinished(Boolean needFinished) {
        this.needFinished = needFinished;
    }

    public Boolean getNeedPeriod() {
        return needPeriod;
    }

    public void setNeedPeriod(Boolean needPeriod) {
        this.needPeriod = needPeriod;
    }

    public Boolean getNeedBudget() {
        return needBudget;
    }

    public void setNeedBudget(Boolean needBudget) {
        this.needBudget = needBudget;
    }

    public Boolean getNeedTreatDrug() {
        return needTreatDrug;
    }

    public void setNeedTreatDrug(Boolean needTreatDrug) {
        this.needTreatDrug = needTreatDrug;
    }

    public Boolean getNeedBandLog() {
        return needBandLog;
    }

    public void setNeedBandLog(Boolean needBandLog) {
        this.needBandLog = needBandLog;
    }

    public UserCalendarSource getSourceType() {
        return sourceType;
    }

    public void setSourceType(UserCalendarSource sourceType) {
        this.sourceType = sourceType;
    }
}
