package cn.mulanbay.schedule.domain;

import cn.mulanbay.schedule.enums.JobExecuteResult;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 调度日志
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Entity
@Table(name = "task_log")
@DynamicInsert
@DynamicUpdate
public class TaskLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2098844185080537512L;

	private Long id;
	private TaskTrigger taskTrigger;
	private Date bussDate;
	private String scheduleIdentityId;
	private Date startTime;
	private Date endTime;
	private Long costTime;
	private JobExecuteResult executeResult;
	private String subTaskExecuteResults;
	private String deployId;
	private String ipAddress;
	private Short redoTimes;
	private Date lastStartTime;
	private Date lastEndTime;
	private String logComment;

	// Constructors

	/** default constructor */
	public TaskLog() {
	}

	/** minimal constructor */
	public TaskLog(Date bussDate, JobExecuteResult executeResult) {
		this.bussDate = bussDate;
		this.executeResult = executeResult;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_trigger_id")
	public TaskTrigger getTaskTrigger() {
		return taskTrigger;
	}

	public void setTaskTrigger(TaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "buss_date", nullable = false, length = 10)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getBussDate() {
		return bussDate;
	}

	public void setBussDate(Date bussDate) {
		this.bussDate = bussDate;
	}

	@Column(name = "schedule_identity_id")
	public String getScheduleIdentityId() {
		return scheduleIdentityId;
	}

	public void setScheduleIdentityId(String scheduleIdentityId) {
		this.scheduleIdentityId = scheduleIdentityId;
	}

	@Column(name = "start_time")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "cost_time")
	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}

	@Column(name = "execute_result")
	public JobExecuteResult getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(JobExecuteResult executeResult) {
		this.executeResult = executeResult;
	}

	@Column(name = "sub_task_execute_results")
	public String getSubTaskExecuteResults() {
		return subTaskExecuteResults;
	}

	public void setSubTaskExecuteResults(String subTaskExecuteResults) {
		this.subTaskExecuteResults = subTaskExecuteResults;
	}

	@Column(name = "deploy_id")
	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	@Column(name = "ip_address")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "redo_times")
	public Short getRedoTimes() {
		return redoTimes;
	}

	public void setRedoTimes(Short redoTimes) {
		this.redoTimes = redoTimes;
	}

	@Column(name = "last_start_time")
	public Date getLastStartTime() {
		return lastStartTime;
	}

	public void setLastStartTime(Date lastStartTime) {
		this.lastStartTime = lastStartTime;
	}

	@Column(name = "last_end_time")
	public Date getLastEndTime() {
		return lastEndTime;
	}

	public void setLastEndTime(Date lastEndTime) {
		this.lastEndTime = lastEndTime;
	}

	@Column(name = "log_comment")
	public String getLogComment() {
		return logComment;
	}

	public void setLogComment(String logComment) {
		this.logComment = logComment;
	}

	@Transient
	public String getExecuteResultName(){
		return executeResult==null ? null : executeResult.getName();
	}


}