package cn.mulanbay.common.thread;

import cn.mulanbay.common.config.OSType;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.MessageNotify;
import cn.mulanbay.common.util.CommandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 操作系统命令执行线程
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class CommandExecuteThread extends EnhanceThread {

	private static final Logger logger = LoggerFactory.getLogger(CommandExecuteThread.class);

	// 异步时间（秒）
	private long asynTime = 5;

	private String cmd;

	private OSType osType;

	private MessageNotify messageNotify;

	public CommandExecuteThread(String cmd, OSType osType) {
		super("启动执行操作系统命令");
		this.cmd = cmd;
		this.osType = osType;
	}

	public CommandExecuteThread(String cmd, OSType osType, MessageNotify messageNotify) {
		super("启动执行操作系统命令");
		this.cmd = cmd;
		this.osType = osType;
		this.messageNotify = messageNotify;
	}

	public long getAsynTime() {
		return asynTime;
	}

	public void setAsynTime(long asynTime) {
		this.asynTime = asynTime;
	}

	@Override
	public void doTask() {
		try {
			sleep(asynTime * 1000);
			String res = CommandUtil.executeCmd(osType, cmd);
			if(messageNotify!=null){
				messageNotify.notifyMsg(ErrorCode.CMD_EXEC_NOTIFY,"命令执行结果通知","命令["+cmd+"]执行结果："+res);
			}
		} catch (Exception e) {
			logger.error("执行操作系统类型为[" + osType + "]的命令:" + cmd + "异常", e);
			if(messageNotify!=null){
				messageNotify.notifyMsg(ErrorCode.CMD_EXEC_ERROR,"命令执行异常","命令["+cmd+"]执行异常："+e.getMessage());
			}
		}
	}

}
