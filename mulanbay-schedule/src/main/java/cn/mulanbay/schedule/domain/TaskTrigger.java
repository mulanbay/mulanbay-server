package cn.mulanbay.schedule.domain;

import cn.mulanbay.schedule.enums.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 调度触发器
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Entity
@Table(name = "task_trigger")
@DynamicInsert
@DynamicUpdate
public class TaskTrigger implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7821258290163948795L;

	private Long id;
	/**
	 * 调度名称
	 */
	private String name;
	/**
	 * 调度用户编号(目前无作用)
	 * 曾经的想法是：用户也可以有自己的调度器，实现起来过于复杂
	 */
	private Long userId;
	/**
	 * 部署点，不同的调度可以部署在不同的服务器上
	 * distriable=true，该调度不支持分布式执行，那么该调度只能在该台服务器执行
	 * distriable=false，该调度可以在所有的服务器上执行，deployId没什么作用
	 */
	private String deployId;
	/**
	 * 具体的调度类,类的全路径
	 */
	private String taskClass;
	/**
	 * 是否支持分布式
	 * 参见deployId说明
	 */
	private Boolean distriable;
	/**
	 * 调度重做类型
	 */
	private RedoType redoType;
	/**
	 * 子调度代码(针对一个调度里面有多个逻辑步骤)，用英文逗号分隔，目前未使用
	 */
	private String subTaskCodes;
	/**
	 * 子调度名称，用英文逗号分隔，目前未使用
	 */
	private String subTaskNames;
	/**
	 * 重做最大的支持次数
	 */
	private Integer allowedRedoTimes;
	/**
	 * 超时时间,单位:毫秒
	 */
	private Long timeout;
	/**
	 * 调度分组名称
	 * 英文名，内部分组使用
	 */
	private String groupName;
	/**
	 * 调度周期类型
	 */
	private TriggerType triggerType;
	/**
	 * 调度的频率，通常和triggerType一起使用
	 * 例如：triggerType=SECOND，triggerInterval=1表示每秒执行一次
	 */
	private Integer triggerInterval;
	/**
	 * 调度的参数，json格式，具体参考taskClass所对应的para的定义
	 */
	private String triggerParas;
	/**
	 * 当triggerType=WEEK时，该值为周的序号，值为1-7，代表周日到周六，中间以英文逗号分隔
	 * 例如:1,2代表周日及周一执行
	 * 当triggerType=CRON时，该值为cron表达式
	 */
	private String cronExpression;
	/**
	 * 业务偏移量，单位是天
	 * 比如一个调度每天执行统计的昨天的数据，那么这里的值=-1
	 */
	private Integer offsetDays;
	/**
	 * 首次执行时间
	 */
	private Date firstExecuteTime;
	/**
	 * 下一次执行时间,为空则使用firstExecuteTime
	 */
	private Date nextExecuteTime;
	/**
	 * 调度状态,调度刷新时判断使用
	 */
	private TriggerStatus triggerStatus;
	/**
	 * 最近一次调度执行结果
	 */
	private JobExecuteResult lastExecuteResult;
	/**
	 * 最近一次调度执行时间
	 */
	private Date lastExecuteTime;
	/**
	 * 总的执行次数
	 */
	private Long totalCount;
	/**
	 * 总的执行失败次数
	 */
	private Long failCount;
	/**
	 * 调度执行是否检查唯一性，避免重复执行
	 * 避免多台调度服务器时钟不同步的问题
	 */
	private Boolean checkUnique;
	private TaskUniqueType uniqueType;
	/**
	 * 调度是否记录调度日志
	 */
	private Boolean loggable;
	/**
	 * 调度在执行失败时是否需要发起通知
	 */
	private Boolean notifiable;
	/**
	 * 调度的执行时间段
	 * 空表示不判断
	 */
	private String execTimePeriods;

	private Date createdTime;
	/**
	 * 该字段表示调度配置有无修改过
	 * 调度刷新时判断使用
	 */
	private Date modifyTime;

	private String comment;
	/**
	 * 调度的版本号，乐观锁实现
	 */
	private long version;

	// Constructors

	/** default constructor */
	public TaskTrigger() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "user_id", nullable = false, length = 20)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "deploy_id", nullable = false, length = 32)
	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	@Column(name = "task_class", nullable = false, length = 100)
	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	@Column(name = "distriable", nullable = false)
	public Boolean getDistriable() {
		return distriable;
	}

	public void setDistriable(Boolean distriable) {
		this.distriable = distriable;
	}

	@Column(name = "redo_type", nullable = false)
	public RedoType getRedoType() {
		return redoType;
	}

	public void setRedoType(RedoType redoType) {
		this.redoType = redoType;
	}

	@Column(name = "sub_task_codes")
	public String getSubTaskCodes() {
		return subTaskCodes;
	}

	public void setSubTaskCodes(String subTaskCodes) {
		this.subTaskCodes = subTaskCodes;
	}

	@Column(name = "sub_task_names")
	public String getSubTaskNames() {
		return subTaskNames;
	}

	public void setSubTaskNames(String subTaskNames) {
		this.subTaskNames = subTaskNames;
	}

	@Column(name = "allowed_redo_times")
	public Integer getAllowedRedoTimes() {
		return allowedRedoTimes;
	}

	public void setAllowedRedoTimes(Integer allowedRedoTimes) {
		this.allowedRedoTimes = allowedRedoTimes;
	}

	@Column(name = "timeout")
	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	@Column(name = "group_name")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "trigger_type")
	public TriggerType getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(TriggerType triggerType) {
		this.triggerType = triggerType;
	}

	@Column(name = "trigger_interval")
	public Integer getTriggerInterval() {
		return triggerInterval;
	}

	public void setTriggerInterval(Integer triggerInterval) {
		this.triggerInterval = triggerInterval;
	}

	@Column(name = "trigger_paras")
	public String getTriggerParas() {
		return triggerParas;
	}

	public void setTriggerParas(String triggerParas) {
		this.triggerParas = triggerParas;
	}


    @Column(name = "cron_expression")
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

	@Column(name = "offset_days")
	public Integer getOffsetDays() {
		return offsetDays;
	}

	public void setOffsetDays(Integer offsetDays) {
		this.offsetDays = offsetDays;
	}

	@Column(name = "first_execute_time")
	public Date getFirstExecuteTime() {
		return firstExecuteTime;
	}

	public void setFirstExecuteTime(Date firstExecuteTime) {
		this.firstExecuteTime = firstExecuteTime;
	}

	@Column(name = "next_execute_time")
	public Date getNextExecuteTime() {
		return nextExecuteTime;
	}

	public void setNextExecuteTime(Date nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	@Column(name = "trigger_status")
	public TriggerStatus getTriggerStatus() {
		return triggerStatus;
	}

	public void setTriggerStatus(TriggerStatus triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

	@Column(name = "last_execute_result")
	public JobExecuteResult getLastExecuteResult() {
		return lastExecuteResult;
	}

	public void setLastExecuteResult(JobExecuteResult lastExecuteResult) {
		this.lastExecuteResult = lastExecuteResult;
	}

	@Column(name = "last_execute_time")
	public Date getLastExecuteTime() {
		return lastExecuteTime;
	}

	public void setLastExecuteTime(Date lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}

	@Column(name = "total_count")
	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	@Column(name = "fail_count")
	public Long getFailCount() {
		return failCount;
	}

	public void setFailCount(Long failCount) {
		this.failCount = failCount;
	}

	@Column(name = "check_unique")
	public Boolean getCheckUnique() {
		return checkUnique;
	}

	public void setCheckUnique(Boolean checkUnique) {
		this.checkUnique = checkUnique;
	}

	@Column(name = "unique_type")
	public TaskUniqueType getUniqueType() {
		return uniqueType;
	}

	public void setUniqueType(TaskUniqueType uniqueType) {
		this.uniqueType = uniqueType;
	}

	@Column(name = "loggable")
	public Boolean getLoggable() {
		return loggable;
	}

	public void setLoggable(Boolean loggable) {
		this.loggable = loggable;
	}

	@Column(name = "notifiable")
	public Boolean getNotifiable() {
		return notifiable;
	}

	public void setNotifiable(Boolean notifiable) {
		this.notifiable = notifiable;
	}

	@Column(name = "exec_time_periods")
	public String getExecTimePeriods() {
		return execTimePeriods;
	}

	public void setExecTimePeriods(String execTimePeriods) {
		this.execTimePeriods = execTimePeriods;
	}

	@Column(name = "created_time")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "TaskTrigger [taskTriggerId=" + id
				+ ", taskTriggerName=" + name + "]";
	}

	@Transient
	public String getLastExecuteResultName(){
		return lastExecuteResult == null ? null : lastExecuteResult.getName();
	}

}
