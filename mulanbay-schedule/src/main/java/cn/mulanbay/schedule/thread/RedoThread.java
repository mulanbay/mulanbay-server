package cn.mulanbay.schedule.thread;

import cn.mulanbay.common.thread.EnhanceThread;
import cn.mulanbay.schedule.QuartzSource;
import cn.mulanbay.schedule.domain.TaskLog;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调度重做
 */
public class RedoThread extends EnhanceThread {

	private static final Logger logger = LoggerFactory.getLogger(RedoThread.class);

	private TaskLog log;

	/**
	 * 是否要更新调度（重做情况下有效）
	 */
	private boolean isUpdateTrigger=false;

	private QuartzSource quartzSource;

	/**
	 * 额外参数，针对手动执行的
	 */
	private Object extraPara;

	public RedoThread(TaskLog log) {
		super("调度日志重做线程,id[" + log.getId() + "]");
		this.log = log;
	}

	public RedoThread(TaskLog log, boolean isUpdateTrigger) {
		super("调度日志重做线程,id[" + log.getId() + "]");
		this.log = log;
		this.isUpdateTrigger = isUpdateTrigger;
	}

	public void setExtraPara(Object extraPara) {
		this.extraPara = extraPara;
	}

	public void setQuartzSource(QuartzSource quartzSource) {
		this.quartzSource = quartzSource;
	}

	@Override
	public void doTask() {
		try {
			// this.sleep(10000);
			String taskClassName = log.getTaskTrigger().getTaskClass();
			AbstractBaseJob job = (AbstractBaseJob) Class.forName(taskClassName).newInstance();
			job.setQuartzSource(quartzSource);
			job.setTaskLog(log);
			job.setUpdateTrigger(isUpdateTrigger);
			job.setRedo(true);
			job.setExtraPara(extraPara);
			job.execute(null);
		} catch (Exception e) {
			logger.error("重做[" + threadName + "]异常", e);
		}
	}

}
