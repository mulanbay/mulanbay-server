package cn.mulanbay.pms.web.bean.request.schedule;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.schedule.enums.RedoType;
import cn.mulanbay.schedule.enums.TaskUniqueType;
import cn.mulanbay.schedule.enums.TriggerStatus;
import cn.mulanbay.schedule.enums.TriggerType;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TaskTriggerFormRequest implements BindUser {

    private Long id;

    @NotEmpty(message = "{validate.taskTrigger.name.notEmpty}")
    private String name;

    //目前没有什么用
    private Long userId;

    @NotEmpty(message = "{validate.taskTrigger.deployId.notEmpty}")
    private String deployId;

    @NotEmpty(message = "{validate.taskTrigger.taskClass.notEmpty}")
    private String taskClass;

    @NotNull(message = "{validate.businessTrip.distriable.NotNull}")
    private Boolean distriable;

    @NotNull(message = "{validate.businessTrip.redoType.NotNull}")
    private RedoType redoType;
    private String subTaskCodes;
    private String subTaskNames;

    @NotNull(message = "{validate.businessTrip.allowedRedoTimes.NotNull}")
    private Integer allowedRedoTimes;

    @NotNull(message = "{validate.businessTrip.timeout.NotNull}")
    private Long timeout;

    @NotEmpty(message = "{validate.taskTrigger.groupName.notEmpty}")
    private String groupName;

    @NotNull(message = "{validate.businessTrip.triggerType.NotNull}")
    private TriggerType triggerType;

    @NotNull(message = "{validate.businessTrip.triggerInterval.NotNull}")
    private Integer triggerInterval;

    private String triggerParas;
    private String cronExpression;

    @NotNull(message = "{validate.businessTrip.offsetDays.NotNull}")
    private Integer offsetDays;

    @NotNull(message = "{validate.businessTrip.firstExecuteTime.NotNull}")
    private Date firstExecuteTime;

    private Date nextExecuteTime;

    @NotNull(message = "{validate.businessTrip.triggerStatus.NotNull}")
    private TriggerStatus triggerStatus;

    @NotNull(message = "{validate.businessTrip.checkUnique.NotNull}")
    private Boolean checkUnique;

    @NotNull(message = "{validate.businessTrip.uniqueType.NotNull}")
    private TaskUniqueType uniqueType;

    @NotNull(message = "{validate.businessTrip.loggable.NotNull}")
    private Boolean loggable;

    @NotNull(message = "{validate.businessTrip.notifiable.NotNull}")
    private Boolean notifiable;

    private String execTimePeriods;

    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public Boolean getDistriable() {
        return distriable;
    }

    public void setDistriable(Boolean distriable) {
        this.distriable = distriable;
    }

    public RedoType getRedoType() {
        return redoType;
    }

    public void setRedoType(RedoType redoType) {
        this.redoType = redoType;
    }

    public String getSubTaskCodes() {
        return subTaskCodes;
    }

    public void setSubTaskCodes(String subTaskCodes) {
        this.subTaskCodes = subTaskCodes;
    }

    public String getSubTaskNames() {
        return subTaskNames;
    }

    public void setSubTaskNames(String subTaskNames) {
        this.subTaskNames = subTaskNames;
    }

    public Integer getAllowedRedoTimes() {
        return allowedRedoTimes;
    }

    public void setAllowedRedoTimes(Integer allowedRedoTimes) {
        this.allowedRedoTimes = allowedRedoTimes;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public Integer getTriggerInterval() {
        return triggerInterval;
    }

    public void setTriggerInterval(Integer triggerInterval) {
        this.triggerInterval = triggerInterval;
    }

    public String getTriggerParas() {
        return triggerParas;
    }

    public void setTriggerParas(String triggerParas) {
        this.triggerParas = triggerParas;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getOffsetDays() {
        return offsetDays;
    }

    public void setOffsetDays(Integer offsetDays) {
        this.offsetDays = offsetDays;
    }

    public Date getFirstExecuteTime() {
        return firstExecuteTime;
    }

    public void setFirstExecuteTime(Date firstExecuteTime) {
        this.firstExecuteTime = firstExecuteTime;
    }

    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public TriggerStatus getTriggerStatus() {
        return triggerStatus;
    }

    public void setTriggerStatus(TriggerStatus triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    public Boolean getCheckUnique() {
        return checkUnique;
    }

    public void setCheckUnique(Boolean checkUnique) {
        this.checkUnique = checkUnique;
    }

    public TaskUniqueType getUniqueType() {
        return uniqueType;
    }

    public void setUniqueType(TaskUniqueType uniqueType) {
        this.uniqueType = uniqueType;
    }

    public Boolean getLoggable() {
        return loggable;
    }

    public void setLoggable(Boolean loggable) {
        this.loggable = loggable;
    }

    public Boolean getNotifiable() {
        return notifiable;
    }

    public void setNotifiable(Boolean notifiable) {
        this.notifiable = notifiable;
    }

    public String getExecTimePeriods() {
        return execTimePeriods;
    }

    public void setExecTimePeriods(String execTimePeriods) {
        this.execTimePeriods = execTimePeriods;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
