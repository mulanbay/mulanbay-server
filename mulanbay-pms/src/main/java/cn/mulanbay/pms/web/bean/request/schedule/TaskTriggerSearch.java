package cn.mulanbay.pms.web.bean.request.schedule;

import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.schedule.enums.TriggerStatus;
import cn.mulanbay.schedule.enums.TriggerType;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class TaskTriggerSearch extends PageSearch {

    @Query(fieldName = "name,taskClass", op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "firstExecuteTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "firstExecuteTime", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "triggerType", op = Parameter.Operator.EQ)
    private TriggerType triggerType;

    @Query(fieldName = "triggerStatus", op = Parameter.Operator.EQ)
    private TriggerStatus triggerStatus;

    @Query(fieldName = "groupName", op = Parameter.Operator.EQ)
    private String groupName;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public TriggerStatus getTriggerStatus() {
        return triggerStatus;
    }

    public void setTriggerStatus(TriggerStatus triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
