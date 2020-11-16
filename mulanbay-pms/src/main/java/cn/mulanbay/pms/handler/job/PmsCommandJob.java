package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.thread.CommandExecuteThread;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.CommandUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.persistent.domain.CommandConfig;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 命令job，执行本地脚本文件或者命令
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class PmsCommandJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(PmsCommandJob.class);

    private PmsCommandJobPara para;

    PmsNotifyHandler pmsNotifyHandler;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        String res = "";
        String cmd = this.getCmd();
        pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
        if (para.isAsyn()) {
            CommandExecuteThread thread = new CommandExecuteThread(cmd, para.getOsType(), pmsNotifyHandler);
            res = "将在" + thread.getAsynTime() + "秒后执行命令:" + cmd;
            thread.start();
            logger.debug(res);
        } else {
            res = CommandUtil.executeCmd(para.getOsType(), cmd);
            logger.debug("执行了命令:" + cmd + ",命令结果：" + res);
        }
        if (res != null && res.length() > 200) {
            // 避免过长
            res = res.substring(0, 200);
        }
        tr.setComment(res);
        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        return tr;
    }

    private String getCmd() {
        String cmd = para.getCmd();
        if (StringUtil.isNotEmpty(cmd)) {
            return cmd;
        } else {
            BaseService baseService = BeanFactoryUtil.getBean(BaseService.class);
            CommandConfig cc = (CommandConfig) baseService.getObject(CommandConfig.class.getSimpleName(), para.getCode(), "code");
            if (cc == null) {
                throw new ApplicationException(ScheduleErrorCode.TRIGGER_PARA_FORMAT_ERROR, "找不到code=" + para.getCode() + "的命令配置");
            } else {
                return cc.getUrl();
            }
        }
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        ParaCheckResult rb = new ParaCheckResult();
        rb.setMessage("参数格式为：1. 命令,2.操作系统类型（-1由程序判断 0LINUX 1WINDOWS）,3. 是否异步（true|false）");
        para = this.getTriggerParaBean();
        if (para == null) {
            rb.setErrorCode(ScheduleErrorCode.TRIGGER_PARA_NULL);
        }
        return rb;
    }

    @Override
    public Class getParaDefine() {
        return PmsCommandJobPara.class;
    }

    public void notifyLog(String cmd) {
        //通知
        String title = "服务器执行了脚本命令";
        pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.CMD_EXECUTED, title,
                "服务器执行了脚本命令：" + cmd, null, null);
    }
}
