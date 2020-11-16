package cn.mulanbay.pms.web.bean.request.schedule;

import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class TaskLogCostTimeStatSearch extends PageSearch implements FullEndDateTime {

    @Query(fieldName = "taskTrigger.id", op = Parameter.Operator.EQ)
    private Long taskTriggerId;

    @Query(fieldName = "startTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "startTime", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "executeResult", op = Parameter.Operator.EQ)
    private JobExecuteResult executeResult;

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

    public Long getTaskTriggerId() {
        return taskTriggerId;
    }

    public void setTaskTriggerId(Long taskTriggerId) {
        this.taskTriggerId = taskTriggerId;
    }

    public JobExecuteResult getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(JobExecuteResult executeResult) {
        this.executeResult = executeResult;
    }
}
