package cn.mulanbay.schedule.job;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.IPAddressUtil;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.schedule.*;
import cn.mulanbay.schedule.domain.TaskLog;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.enums.TaskUniqueType;
import cn.mulanbay.schedule.enums.TriggerStatus;
import cn.mulanbay.schedule.enums.TriggerType;
import cn.mulanbay.schedule.lock.LockStatus;
import cn.mulanbay.schedule.lock.ScheduleLocker;
import cn.mulanbay.schedule.para.TriggerExecTimePeriods;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 所有调度任务的基类，定义调度的流程
 * 同时支持新建的调度和重做的调度
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public abstract class AbstractBaseJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(AbstractBaseJob.class);

	protected final static ParaCheckResult DEFAULT_SUCCESS_PARA_CHECK =new ParaCheckResult();

	/**
	 * 调度资源(持久层处理器、分布式支持等)
	 */
	protected QuartzSource quartzSource;

	/**
	 * 调度触发器（无论新建还是重做，都必须有该对象）
	 */
	private TaskTrigger taskTrigger;

	/**
	 * 是否重做调度
	 */
	private boolean isRedo = false;

	/**
	 * 只有重做的情况下才会有该对象
	 */
	private TaskLog taskLog;

	/**
	 * 该调度是否正在执行，解决调度的执行时长大于调度周期值
	 * 比如：该调度的执行周期值为5秒，而调度的逻辑功能执行了5秒，导致本次调度还没执行完就开始下一次的调度周期了
	 */
	private boolean isDoing = false;

	/**
	 * 是否检查过参数
	 */
	private boolean isParaChecked = false;

	/**
	 * 是否要更新调度（重做情况下有效）
	 * 重做分为两种：A 已经执行过，但是执行失败了
	 *             B 该运营日没有执行(程序挂了等原因)，需要手动进行执行
	 */
	private boolean isUpdateTrigger;

	private JobExecutionContext jobExecutionContext;

	/**
	 * 调度的触发时间
	 */
	private Date scheduledFireTime;

	/**
	 * 额外参数，针对手动执行的
	 */
	private Object extraPara;

	public TaskTrigger getTaskTrigger() {
		if (isRedo) {
			return taskLog.getTaskTrigger();
		}
		return taskTrigger;
	}

	public Object getExtraPara() {
		return extraPara;
	}

	public void setExtraPara(Object extraPara) {
		this.extraPara = extraPara;
	}

	public void setQuartzSource(QuartzSource quartzSource) {
		this.quartzSource = quartzSource;
	}

	public TaskLog getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}

	public boolean isRedo() {
		return isRedo;
	}

	public void setRedo(boolean isRedo) {
		this.isRedo = isRedo;
	}

	public void setUpdateTrigger(boolean isUpdateTrigger) {
		this.isUpdateTrigger = isUpdateTrigger;
	}

	public AbstractBaseJob() {
		super();
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if(arg0!=null){
			scheduledFireTime=arg0.getScheduledFireTime();
		}
		if (isRedo) {
			redoJob(arg0);
		} else {
			newJob(arg0);
		}
	}

	/**
	 * 新的任务调度
	 *
	 * @param arg0
	 * @throws JobExecutionException
	 */
	private void newJob(JobExecutionContext arg0) throws JobExecutionException {
		LockStatus lockStatus = null;
		try {
			jobExecutionContext = arg0;
			// 获取调度器
			taskTrigger = (TaskTrigger) arg0.getMergedJobDataMap().get(
					QuartzConstant.SCHEDULE_TASK_TRIGGER);
			quartzSource = (QuartzSource) arg0.getMergedJobDataMap().get(
					QuartzConstant.SCHEDULE_QUARTZ_SOURCE);
			boolean checkUnique = this.checkScheduleExecUnique();
			if(!checkUnique){
				logger.error("调度任务id="+taskTrigger.getId()+"重复执行");
				return;
			}
			lockStatus = this.lock();
			if(lockStatus!=LockStatus.SUCCESS){
				return;
			}
			logger.debug("开始执行[" + taskTrigger.getName() + "]");
			taskLog = new TaskLog();
			// TODO线程同步
			if (isDoing) {
				logger.debug("任务[" + taskTrigger.getName()
						+ "]已经在执行");
				taskLog.setLogComment("任务[" + taskTrigger.getName()
						+ "]已经在执行，跳过本次执行。");
				taskTrigger.setLastExecuteResult(JobExecuteResult.DUPLICATE);
				return;
			}
			isDoing = true;
			Date startTime = new Date();
			// 新的
			logger.debug("新的JOB[" + taskTrigger.getName() + "]");
			taskLog.setStartTime(startTime);
			taskLog.setTaskTrigger(taskTrigger);
			//taskTrigger.setLastExecuteTime(startTime);

			// 执行任务
			TaskResult ar = checkParaAndDoTask();

			taskLog.setExecuteResult(ar.getExecuteResult());
			taskLog.setSubTaskExecuteResults(ar.getSubTaskExecuteResults());
			taskLog.setLogComment(appendComment(taskLog.getLogComment(),ar.getComment()));

			// TODO 需要重新加载taskTrigger
			taskTrigger.setLastExecuteResult(ar.getExecuteResult());
			logger.debug("[" + taskTrigger.getName() + "]执行结束");
		} catch (ApplicationException ae) {
			logger.error("[" + taskTrigger.getName() + "]执行异常", ae);
			taskTrigger.setLastExecuteResult(JobExecuteResult.FAIL);
			taskLog.setExecuteResult(JobExecuteResult.FAIL);
			taskLog.setLogComment(appendComment(taskLog.getLogComment(),
					"调度执行遇到ApplicationException。"+ae.getMessageDetail()));
		} catch (Exception e) {
			logger.error("[" + taskTrigger.getName() + "]执行异常", e);
			taskTrigger.setLastExecuteResult(JobExecuteResult.FAIL);
			taskLog.setExecuteResult(JobExecuteResult.FAIL);
			taskLog.setLogComment(appendComment(taskLog.getLogComment(),e.getMessage()));
		} finally {
			long costTime=0L;
			if(lockStatus ==LockStatus.SUCCESS){
				try {
					isDoing = false;
					Date endTime = new Date();
                    costTime = endTime.getTime()-taskLog
                            .getStartTime().getTime();
					SchedulePersistentProcessor processor = this.getPersistentProcessor();

					taskTrigger.setNextExecuteTime(arg0.getNextFireTime());
					taskTrigger.setTotalCount(taskTrigger.getTotalCount() + 1);
					if (taskTrigger.getLastExecuteResult() == JobExecuteResult.FAIL) {
						taskTrigger.setFailCount(taskTrigger.getFailCount() + 1);
					}
					taskTrigger.setLastExecuteTime(new Date());
					if(taskTrigger.getTriggerType()== TriggerType.NOW){
						//一次类型的设为无效
						taskTrigger.setTriggerStatus(TriggerStatus.DISABLE);
					}
					// 更新执行调度
					processor.updateTaskTriggerForNewJob(taskTrigger);
					//需要记录日志或者没运行成功的都需要记录
					if(taskTrigger.getLoggable()||JobExecuteResult.FAIL==taskLog.getExecuteResult()){
						// 新的
						taskLog.setEndTime(endTime);
						taskLog.setBussDate(getBussDay());
						taskLog.setCostTime(costTime);
						taskLog.setRedoTimes((short) 0);
						// 保存执行日志
						taskLog.setIpAddress(getIpAddress());
						taskLog.setLogComment(appendComment(taskLog.getLogComment(),""));
						taskLog.setDeployId(quartzSource.getDeployId());
						String scheduleIdentityId = this.generateScheduleIdentityId();
						taskLog.setScheduleIdentityId(scheduleIdentityId);
						processor.saveTaskLog(taskLog);
					}
					notifyMessage();
				} catch (Exception e) {
					logger.error("保存执行记录异常", e);
				}
				// 解锁
				unlock(costTime/1000);
			}
		}
	}

	/**
	 * 重做任务调度
	 *
	 * @param arg0
	 * @throws JobExecutionException
	 */
	private void redoJob(JobExecutionContext arg0) throws JobExecutionException {
		LockStatus lockStatus = null;
		try {
			jobExecutionContext = arg0;
			taskTrigger = taskLog.getTaskTrigger();
			lockStatus = this.lock();
			if(lockStatus!=LockStatus.SUCCESS){
				return;
			}
			logger.debug("开始重做任务[" + taskTrigger.getName() + "]");
			Date startTime = new Date();
			if (taskLog.getId() == null) {
				taskLog.setStartTime(startTime);
			} else {
				taskLog.setLastStartTime(startTime);
			}
			// 执行任务
			TaskResult ar = checkParaAndDoTask();

			taskLog.setExecuteResult(ar.getExecuteResult());
			taskLog.setSubTaskExecuteResults(ar.getSubTaskExecuteResults());
			taskLog.setLogComment(ar.getComment());
			logger.debug("[" + taskLog.getTaskTrigger().getName()
					+ "]执行结束");
		} catch (ApplicationException ae) {
			logger.error("[" + taskTrigger.getName() + "]执行异常", ae);
			taskTrigger.setLastExecuteResult(JobExecuteResult.FAIL);
			taskLog.setExecuteResult(JobExecuteResult.FAIL);
			taskLog.setLogComment(appendComment(taskLog.getLogComment(),
					"调度执行遇到ApplicationException。"+ae.getMessageDetail()));
		} catch (Exception e) {
			logger.error("[" + taskLog.getTaskTrigger().getName()
					+ "]执行异常", e);
			taskLog.setExecuteResult(JobExecuteResult.FAIL);
			taskLog.setLogComment(appendComment(taskLog.getLogComment(),e.getMessage()));
		} finally {
			if(lockStatus ==LockStatus.SUCCESS){
				try {
					Date endTime = new Date();
					SchedulePersistentProcessor processor = this.getPersistentProcessor();
					taskLog.setIpAddress(getIpAddress());
					taskLog.setDeployId(quartzSource.getDeployId());
					if (taskLog.getId() == null) {
						String lc = taskLog.getLogComment() == null ? "" : taskLog
								.getLogComment();
						taskLog.setLogComment(lc + "[未执行，手动重做]");
						taskLog.setEndTime(endTime);
						taskLog.setRedoTimes((short) 0);
						taskLog.setCostTime((taskLog.getEndTime().getTime() - taskLog
								.getStartTime().getTime()));
						taskLog.setLogComment(appendComment(taskLog.getLogComment(),""));
						String scheduleIdentityId = this.generateScheduleIdentityId();
						taskLog.setScheduleIdentityId(scheduleIdentityId);
						processor.saveTaskLog(taskLog);
					} else {
						taskLog.setLastEndTime(endTime);
						short redoTime = (taskLog.getRedoTimes() == null ? 0
								: taskLog.getRedoTimes().shortValue());
						taskLog.setRedoTimes((short) (redoTime + 1));
						taskLog.setCostTime((taskLog.getLastEndTime().getTime() - taskLog
								.getLastStartTime().getTime()));
						taskLog.setLogComment(appendComment(taskLog.getLogComment(),""));
						processor.updateTaskLog(taskLog);
					}
					if (isUpdateTrigger) {
						taskTrigger.setTotalCount(taskTrigger.getTotalCount() + 1);
						if (taskLog.getExecuteResult() == JobExecuteResult.FAIL) {
							taskTrigger
									.setFailCount(taskTrigger.getFailCount() + 1);
						}
						taskTrigger
								.setLastExecuteResult(taskLog.getExecuteResult());
						taskTrigger.setLastExecuteTime(endTime);
						// 通知调度器更新
						//taskTrigger.setModifyTime(new Date());
						processor.updateTaskTriggerForRedoJob(taskTrigger);
					}
				} catch (Exception e) {
					logger.error("保存执行记录异常", e);
				}
				notifyMessage();
				unlock(taskLog.getCostTime()/1000);
			}
		}
	}

	/**
	 * 是否关闭
	 * @return
	 */
	protected boolean isShutDown(){
		try {
			if(jobExecutionContext==null){
				return false;
			}
			return jobExecutionContext.getScheduler().isShutdown();
		} catch (Exception e) {
			logger.error("获取调度是否关闭异常",e);
			return false;
		}
	}
	/**
	 * 生成唯一的调度编号
	 * @return
	 */
	private String generateScheduleIdentityId(){
		String dateTimeString = DateUtil.getFormatDate(scheduledFireTime,DateUtil.Format24Datetime2);
		return taskTrigger.getId()+"_"+dateTimeString;
	}

	/**
	 * 上锁，只针对分布式调度的有效
	 * @return
	 */
	private LockStatus lock(){
		if(!taskTrigger.getDistriable()){
			//不支持分布式的不需要上锁
			return LockStatus.SUCCESS;
		}
		ScheduleLocker scheduleLocker = quartzSource.getScheduleLocker();
		if(scheduleLocker==null){
			logger.debug("没有调度锁配置，无法进行锁");
			return LockStatus.SUCCESS;
		}else{
			String identityKey = getIdentityKey();
			LockStatus lockStatus= scheduleLocker.lock(identityKey,taskTrigger.getTimeout());
			logger.debug("调度锁key="+identityKey+",上锁结果:"+lockStatus);
			return lockStatus;
		}

	}

	/**
	 * 解锁，只针对分布式调度的有效
	 * (1)这里其实会涉及到一个时钟同步问题，不同的主机时钟差别很大，那么时间很难控制。
	 * 支持分布式的任务如果其任务执行时间很快最好在同一台主机不同的应用上跑
	 * (2)另外一种identifyKey通过JobExecutionContext的ScheduleFireTime来确定唯一性(精确到秒)
	 * 可以解决时钟不同步的问题
	 * (3)目前实现方式：添加scheduleIdentityId检查，即使时钟不同步也无问题
	 * @param costSeconds
	 * @return
	 */
	private LockStatus unlock(long costSeconds){
		if(!taskTrigger.getDistriable()){
			//不支持分布式的不需要上锁
			return LockStatus.SUCCESS;
		}
		if(costSeconds<quartzSource.getDistriTaskMinCost()){
			try {
				Thread.sleep((quartzSource.getDistriTaskMinCost()-costSeconds)*1000L);
			} catch (Exception e) {
				logger.error("unlock sleep error",e);
			}
		}
		ScheduleLocker scheduleLocker = quartzSource.getScheduleLocker();
		if(scheduleLocker==null){
			return LockStatus.SUCCESS;
		}else{
			String identityKey = getIdentityKey();
			LockStatus lockStatus =  scheduleLocker.unlock(getIdentityKey());
			logger.debug("调度锁key="+identityKey+",解锁结果:"+lockStatus);
			return lockStatus;
		}

	}

	/**
	 * 每次调度任务执行的唯一编号
	 * @return
	 */
	private String getIdentityKey(){
		String prefix= (isRedo ? "redo":"new");
		return prefix+"_"+taskTrigger.getGroupName()+"_"+taskTrigger.getId();
	}

	/**
	 * 消息通知
	 * 针对执行失败操作
	 */
	private void notifyMessage(){
		if(quartzSource.getNotifiableProcessor()!=null){
			JobExecuteResult jer = taskLog.getExecuteResult();
			if(jer==JobExecuteResult.FAIL||jer==JobExecuteResult.DUPLICATE){
				Long taskTriggerId = taskTrigger.getId();
				String title="调度器["+taskTrigger.getName()+"]调度执行异常";
				String content="调度器["+taskTrigger.getName()+"]调度在"+
						DateUtil.getFormatDate(new Date(),DateUtil.Format24Datetime)+"执行异常,执行结果:"+jer.getName()+"，错误信息:"+
						taskLog.getLogComment();
				this.notifyMessage(taskTriggerId,ScheduleErrorCode.TRIGGER_EXEC_ERROR,title,content);
			}
		}
	}

	private void notifyMessage(Long taskTriggerId,int code, String title, String message){
		if(quartzSource.getNotifiableProcessor()!=null){
			quartzSource.getNotifiableProcessor().notifyMessage(taskTriggerId,code,title,message);
		}else {
			logger.warn("系统没有配置提醒处理器，无法发送消息");
		}
	}
	/**
	 * 业务操作，具体的业务逻辑
	 * 由子类来实现
	 * @return
	 */
	public abstract TaskResult doTask();

	/**
	 * 检查调度执行是否唯一
	 * @return
	 */
	private boolean checkScheduleExecUnique(){
		if(taskTrigger.getCheckUnique()){
			if(taskTrigger.getUniqueType()== TaskUniqueType.IDENTITY){
				String scheduleIdentityId = this.generateScheduleIdentityId();
				boolean isExit = this.getPersistentProcessor().isTaskLogExit(scheduleIdentityId);
				if(isExit){
					return false;
				}
			}else{
				boolean isExit = this.getPersistentProcessor().isTaskLogExit(taskTrigger.getId(),this.getBussDay());
				if(isExit){
					return false;
				}
			}
		}
		return true;
	}


	private TaskResult checkParaAndDoTask() {
		if(!checkNeedExec()){
			return new TaskResult(JobExecuteResult.SKIP);
		}
		if (isParaChecked) {
			// 已经检查过
			return doTask();
		} else {
			logger.info("检查[" + getTaskTrigger().getName() + "]的参数:"
					+ getTaskTrigger().getTriggerParas());
			ParaCheckResult pcr = checkTriggerPara();
			if (pcr.getErrorCode() != ErrorCode.SUCCESS) {
				TaskResult tr = new TaskResult();
				tr.setExecuteResult(JobExecuteResult.FAIL);
				tr.setComment("检查参数异常，错误代码：" + pcr.getErrorCode() + ","
						+ pcr.getMessage());
				return tr;
			} else {
				isParaChecked = true;
				return doTask();
			}
		}
	}

	/**
	 * 获取业务统计的日期,只精确到天
	 *
	 * @return
	 */
	public Date getBussDay() {
		if (isRedo) {
			return taskLog.getBussDate();
		} else {
			int offsetDays = taskTrigger.getOffsetDays().intValue();
			//需要根据调度时间来计算，因为有可能很多年没有执行，quartz会从最近的一次开始调度
			Date d = DateUtil.getDate(offsetDays,scheduledFireTime);
			return d;
		}
	}

	private String getIpAddress() {
		return IPAddressUtil.getLocalIpAddress();
	}

	private SchedulePersistentProcessor getPersistentProcessor() {
		return quartzSource.getSchedulePersistentProcessor();
	}

	/**
	 * 检查是否需要执行
	 * 参数格式：01:00-02:20,03:30-17:40
	 * @return
	 */
	private boolean checkNeedExec(){
		try {
			String execTimePeriods = taskTrigger.getExecTimePeriods();
			if(StringUtil.isEmpty(execTimePeriods)){
				return true;
			}else{
				TriggerExecTimePeriods etp = (TriggerExecTimePeriods) JsonUtil.jsonToBean(execTimePeriods,TriggerExecTimePeriods.class);
				if(etp==null){
					return true;
				}
				Date now = new Date();
				//星期判断
				boolean we = checkWeekExec(now,etp.getWeeks());
				//时间段判断
				boolean te = checkTimeExec(now,etp.getTimes());
				if(we&&te){
					return true;
				}else{
					logger.debug("ID["+taskTrigger.getId()+"],name["+taskTrigger.getName()+"]的调度在当前时间配置为不执行,星期判断:"+we+",时间段判断:"+te);
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("检查调度是否需要执行异常",e);
			this.notifyMessage(taskTrigger.getId(),ScheduleErrorCode.TRIGGER_EXEC_PARA_CHECK_ERROR,
					"检查调度是否需要执行异常",e.getMessage());
			return false;
		}
	}

	/**
	 * 检查星期的执行判断
	 * @param now
	 * @param conf
	 * @return
	 */
	private boolean checkWeekExec(Date now,int[] conf){
		if(conf!=null&&conf.length>0){
			int weekIndex = DateUtil.getDayIndexInWeek(now);
			for(int w : conf){
				if(weekIndex==w){
					return true;
				}
			}
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 检查时间段的执行判断
	 * @param now
	 * @param conf
	 * @return
	 */
	private boolean checkTimeExec(Date now,String conf){
		if(StringUtil.isEmpty(conf)){
			return true;
		}else{
			String today = DateUtil.getToday();
			for(String s : conf.split(",")){
				String[] hms = s.split("-");
				Date begin = DateUtil.getDate(today+" "+hms[0]+":00",DateUtil.Format24Datetime);
				Date end = DateUtil.getDate(today+" "+hms[1]+":00",DateUtil.Format24Datetime);
				if(now.after(begin)&&now.before(end)){
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * 设置备注
	 *
	 * @param oc 原来的备注
	 * @param as 需要添加的备注
	 * @return
	 */
	private String appendComment(String oc,String as) {
		String s = (oc ==null ? "":oc);
		s+="。"+as;
		if (s == null || s.isEmpty()) {
			return s;
		} else {
			if (s.length() > 255) {
				// 避免过长
				s = s.substring(0, 255);
			}
			return s;
		}
	}

	/**
	 * 调度是否正在被执行
	 * @return
	 */
	public boolean isJobDoing(){
		return isDoing;
	}

	/**
	 * 检查调度的参数
	 *
	 * @return
	 */
	public abstract ParaCheckResult checkTriggerPara();

	/**
	 * 调度参数（json格式数据）对应的实体类类，如果没有则为空
	 * @return
	 */
	public abstract Class getParaDefine();

	/**
	 * 获取调度参数的实体类
	 * @return
	 */
	public <T> T getTriggerParaBean(){
		try {
			Class c = this.getParaDefine();
			if(c==null){
				return null;
			}else{
				String triggerPara = getTaskTrigger().getTriggerParas();
				if(StringUtil.isEmpty(triggerPara)){
					return null;
				}else{
					return (T) JsonUtil.jsonToBean(triggerPara,c);
				}
			}
		} catch (Exception e) {
			logger.error("获取调度参数异常",e);
			throw new ApplicationException(ScheduleErrorCode.JOB_PARA_PARSE_ERROR,
					"获取调度参数异常:"+e.getMessage());
		}
	}
}
