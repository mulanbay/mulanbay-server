package cn.mulanbay.schedule;

import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;

/**
 * 调度服务器
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class QuartzServer {

	private static final Logger logger = LoggerFactory.getLogger(QuartzServer.class);

	private StdSchedulerFactory factory;

	private Scheduler scheduler;

	private QuartzSource quartzSource;

	private boolean scheduleStatus = true;

	/**
	 * 任务数
	 */
	private int jobs=0;

	public QuartzServer() {
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {

		try {
			logger.debug("开始初始化调度工厂");
			factory = new StdSchedulerFactory();
			scheduler = factory.getScheduler();
			scheduler.start();
			logger.debug("初始化调度工厂结束");

		} catch (Exception e) {
			logger.error("初始化调度工厂异常", e);
		}

	}

	public QuartzSource getQuartzSource() {
		return quartzSource;
	}

	public void setQuartzSource(QuartzSource quartzSource) {
		this.quartzSource = quartzSource;
	}

	public boolean getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(boolean scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	/**
	 * 是否已经被调度
	 *
	 * @param taskTriggerId
	 * @return
	 */
	public boolean isTaskTriggerExecuting(long taskTriggerId) {
		try {
			List<JobExecutionContext> list = scheduler
					.getCurrentlyExecutingJobs();
			if (list == null || list.isEmpty()) {
				return false;
			} else {
				for (JobExecutionContext jc : list) {
					JobDetail job = jc.getJobDetail();
					TaskTrigger tt = (TaskTrigger) job.getJobDataMap().get(
							QuartzConstant.SCHEDULE_TASK_TRIGGER);
					if (tt.getId() == taskTriggerId) {
						return true;
					}
				}
				return false;
			}
		} catch (Exception e) {
			logger.error("获取调度任务是否正在执行异常", e);
			return false;
		}
	}

	/**
	 * 获取触发器被添加的时间
	 *
	 * @param taskTriggerId
	 * @return
	 */
	public Date getAddTime(Long taskTriggerId,String groupName) {
		try {
			JobKey key = JobKey.jobKey(taskTriggerId.toString(),
					groupName);
			JobDetail job = scheduler.getJobDetail(key);
			if(job==null){
				return null;
			}else{
				Date dd = (Date) job.getJobDataMap().get(
						QuartzConstant.SCHEDULE_ADD_TIME);
				return dd;
			}
		} catch (Exception e) {
			logger.error("获取触发器被添加的时间异常",e);
			return null;
		}
	}

	/**
	 * 获取调度系统里的触发器信息
	 *
	 * @param taskTriggerId
	 * @return
	 */
	public TaskTrigger getScheduledTaskTrigger(Long taskTriggerId,String groupName) {
		try {
			JobKey key = JobKey.jobKey(taskTriggerId.toString(),
					groupName);
			JobDetail job = scheduler.getJobDetail(key);
			if(job==null){
				return null;
			}else{
				TaskTrigger tt = (TaskTrigger) job.getJobDataMap().get(
						QuartzConstant.SCHEDULE_TASK_TRIGGER);
				return tt;
			}
		} catch (Exception e) {
			logger.error("获取调度系统里的触发器信息异常",e);
			return null;
		}
	}

	/**
	 * 返回正在调度执行的job数
	 *
	 * @return
	 */
	public int getCurrentlyExecutingJobsCount() {
		try {
			return scheduler.getCurrentlyExecutingJobs().size();
		} catch (Exception e) {
			logger.error("获取正在调度执行的job数异常", e);
			return -1;
		}
	}

	/**
	 * 返回正在调度执行的job
	 *
	 */
	public List<TaskTrigger> getCurrentlyExecutingJobs() {
		List<TaskTrigger> list = new ArrayList<TaskTrigger>();
		try {
			List<JobExecutionContext> jecs = scheduler
					.getCurrentlyExecutingJobs();
			for (JobExecutionContext jec : jecs) {
				TaskTrigger tt = (TaskTrigger) jec.getJobDetail()
						.getJobDataMap().get(QuartzConstant.SCHEDULE_TASK_TRIGGER);
				list.add(tt);
			}
		} catch (Exception e) {
			logger.error("获取正在调度执行的job异常", e);
		}
		return list;
	}

	/**
	 * 调度是否正在被执行
	 *
	 * @return
	 */
	public boolean isExecuting(Long taskTriggerId) {
		try {
			List<JobExecutionContext> jecs = scheduler
					.getCurrentlyExecutingJobs();
			for (JobExecutionContext jec : jecs) {
				TaskTrigger tt = (TaskTrigger) jec.getJobDetail()
						.getJobDataMap().get(QuartzConstant.SCHEDULE_TASK_TRIGGER);
				if(taskTriggerId.longValue()==tt.getId().longValue()){
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			logger.error("是否正在被执行异常", e);
			return false;
		}
	}

	/**
	 * 获取调度器的job数
	 * @return
	 */
	public int getScheduleJobsCount() {
		return jobs;
	}

	/**
	 * 刷新调度
	 *
	 * @param isForce
	 *            1.非强制刷新：只有变化的才会去刷新<br>
	 *            2.强制刷新：不考虑是否变化，全部刷新<br>
	 */
	public void refreshSchedule(boolean isForce) {
		scheduleTask(isForce);
	}

	/**
	 * 调度任务
	 *
	 * @param isForce
	 *            1.非强制刷新：只有变化的才会去刷新<br>
	 *            2.强制刷新：不考虑是否变化，全部刷新<br>
	 */
	private void scheduleTask(boolean isForce) {
		try {
			logger.debug("任务调度开始");
			if (isForce) {
				this.clear();
				logger.warn("进行调度强制刷新，清空所有调度");
			}
			if(!scheduleStatus){
				this.clear();
				logger.warn("调度已经停止，清空所有调度");
				return;
			}
			int changeCount = 0;
			List<TaskTrigger> triggerList = quartzSource.getSchedulePersistentProcessor().getActiveTaskTrigger(
					quartzSource.getDeployId(),quartzSource.isSupportDistri());
			if (triggerList == null || triggerList.isEmpty()) {
				this.clear();
				logger.warn("触发器列表为空，清空所有调度");
				return;
			}
			logger.debug("开始更新调度，待比较更新的调度数："+triggerList.size());
			// 更新调度
			for (TaskTrigger ts : triggerList) {
				JobKey key = JobKey.jobKey(ts.getId().toString(),
						ts.getGroupName());
				JobDetail job = scheduler.getJobDetail(key);
				if (job == null) {
					logger.info("任务：" + ts.getName() + "["
							+ ts.getId() + "]不存在，新增一个任务");
					addTask(ts);
					changeCount++;
				} else {
					TaskTrigger oldTs = (TaskTrigger) job.getJobDataMap().get(
							QuartzConstant.SCHEDULE_TASK_TRIGGER);
					if (ts.getModifyTime() == null) {
						// 说明没有修改，不做处理
						logger.debug("任务：" + ts.getName() + "["
								+ ts.getId() + "]没有变化");
					} else {
						if (oldTs.getModifyTime() == null
								|| ts.getModifyTime().getTime() > oldTs
										.getModifyTime().getTime()) {
							// 修改过
							this.deleteTask(key);
							logger.info("移除一个过时的任务：" + ts.getName()
									+ "[" + ts.getId() + "]，重新调度");
							addTask(ts);
							changeCount++;
						}
					}

				}
			}
			// 没有的要移除
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey key : jobKeys) {
				String name = key.getName();
				String group = key.getGroup();
				logger.debug("[key]name：" + name + ",group:" + group);
				boolean isExit = isInActiveTrigger(name, group,triggerList);
				if (!isExit) {
					this.deleteTask(key);
					logger.info("移除一个废弃的任务：" + name);
					changeCount++;
				}
			}
			logger.debug("任务调度结束. 任务修改数量：" + changeCount);
		} catch (Exception e) {
			logger.error("调度任务异常", e);
		}
	}

	/**
	 * 刷新某个调度
	 * @param ts
	 * @return
	 */
	public boolean refreshTask(TaskTrigger ts){
		try {
			JobKey key = JobKey.jobKey(ts.getId().toString(),
					ts.getGroupName());
			String name = key.getName();
			String group = key.getGroup();
			logger.debug("[key]name：" + name + ",group:" + group);
			JobDetail job = scheduler.getJobDetail(key);
			if (job!=null) {
				this.deleteTask(key);
				logger.info("移除原来已经存在的需要刷新的任务：" + name);
			}else {
				logger.info("原来已经存在的需要刷新的任务：" + name+"在调度器里面不存在");
			}
			addTask(ts);
			logger.info("添加需要刷新的任务：" + name);
			return true;
		} catch (Exception e) {
			logger.error("刷新某个调度异常",e);
			return false;
		}
	}

	/**
	 * 清空调度任务
	 *
	 */
	public void clear() {
		try {
			logger.warn("开始清空调度任务");
			scheduler.clear();
			jobs=0;
			logger.warn("清空调度任务结束");
		} catch (Exception e) {
			logger.error("清空调度任务异常", e);
		}
	}

	/**
	 * 关闭调度服务器
	 * 如果应用不是被强行关闭，那么需要手动关闭调度服务
	 *
	 * @param waitForJobsToComplete
	 */
	public void shutdown(boolean waitForJobsToComplete) {
		try {
			logger.warn("开始关闭调度服务器");
			this.clear();
			scheduler.shutdown(waitForJobsToComplete);
			logger.warn("关闭调度服务器结束");
		} catch (Exception e) {
			logger.error("关闭调度服务器异常", e);
		}
	}

	private boolean isInActiveTrigger(String name, String group,List<TaskTrigger> triggerList) {
		for (TaskTrigger ts : triggerList) {
			if (ts.getId().toString().equals(name)
					&& ts.getGroupName().equals(group)) {
				return true;
			}
		}
		return false;
	}

	public void deleteTask(JobKey key){
		try {
			scheduler.deleteJob(key);
			jobs--;
		} catch (Exception e) {
			logger.error("删除调度任务异常,key="+key,e);
		}
	}

	/**
	 * 添加一个调度
	 *
	 * @param ts
	 */
	private void addTask(TaskTrigger ts) {
		try {
			String className = ts.getTaskClass();
			Class<AbstractBaseJob> jobClass = (Class<AbstractBaseJob>) Class.forName(className);

			JobDetail jobDetail = newJob(jobClass).withIdentity(
					ts.getId().toString(), ts.getGroupName())
					.build();
			// 设置参数
			jobDetail.getJobDataMap()
					.put(QuartzConstant.SCHEDULE_TASK_TRIGGER, ts);
			jobDetail.getJobDataMap()
					.put(QuartzConstant.SCHEDULE_QUARTZ_SOURCE, quartzSource);
			jobDetail.getJobDataMap()
					.put(QuartzConstant.SCHEDULE_ADD_TIME, new Date());

			Trigger trigger = TriggerFactory.createTrigger(ts);

			scheduler.scheduleJob(jobDetail, trigger);
			logger.info("添加一个任务：" + ts.getName() + "["
					+ ts.getId() + "]");
			jobs++;
			logger.debug("当前job数："+jobs);
		} catch (Exception e) {
			logger.error("增加任务异常", e);
		}
	}

}
