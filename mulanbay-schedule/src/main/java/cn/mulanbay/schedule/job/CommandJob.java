package cn.mulanbay.schedule.job;


import cn.mulanbay.common.util.CommandUtil;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.common.thread.CommandExecuteThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 执行操作系统命令
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class CommandJob extends AbstractBaseJob {

	private static final Logger logger = LoggerFactory.getLogger(CommandJob.class);

	private CommandJobPara para;

	@Override
	public TaskResult doTask() {
		TaskResult tr = new TaskResult();
		String res = "";
		if (para.isAsyn()) {
			CommandExecuteThread thread = new CommandExecuteThread(para.getCmd(), para.getOsType());
			res = "将在" + thread.getAsynTime() + "秒后执行命令:" + para.getCmd();
			thread.start();
			logger.debug(res);
		} else {
			res = CommandUtil.executeCmd(para.getOsType(), para.getCmd());
			logger.debug("执行了命令:" + para.getCmd() + ",命令结果：" + res);
		}
		if (res != null && res.length() > 200) {
			// 避免过长
			res = res.substring(0, 200);
		}
		tr.setComment(res);
		tr.setExecuteResult(JobExecuteResult.SUCCESS);
		return tr;
	}

	/**
	 * 提醒通知
	 * @param cmd
	 */
	public void notifyLog(String cmd){

	}

	@Override
	public ParaCheckResult checkTriggerPara() {
		ParaCheckResult rb = new ParaCheckResult();
		rb.setMessage("参数格式为：1. 命令,2.操作系统类型（-1由程序判断 0LINUX 1WINDOWS）,3. 是否异步（true|false）");
		para = this.getTriggerParaBean();
		if(para==null){
			rb.setErrorCode(ScheduleErrorCode.TRIGGER_PARA_NULL);
		}
		return rb;
	}

	@Override
	public Class getParaDefine() {
		return CommandJobPara.class;
	}

}
