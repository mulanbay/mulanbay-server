package cn.mulanbay.schedule.job;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.domain.TaskLog;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.thread.RedoThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 *
 * 自动错做的调度
 * 有些调度执行失败后，可能当时因为网络原因或资源原因
 * 自动重做调度自动化重做这些调度
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class AutoRedoJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(AutoRedoJob.class);

    private AutoRedoJobPara para;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        Date startDate = DateUtil.getDate(0-para.getStartDays());
        Date endDate = DateUtil.getDate(0-para.getEndDays());
        List<TaskLog> list = this.quartzSource.getSchedulePersistentProcessor().
                getAutoRedoTaskLogs(this.quartzSource.getDeployId(),this.quartzSource.isSupportDistri(),startDate,endDate);
        if(StringUtil.isEmpty(list)){
            logger.debug("没有需要自动重做的任务");
        }else{
            for(TaskLog taskLog:list){
                autoRedo(taskLog);
            }
        }
        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        tr.setComment("一共自动重做了"+list.size()+"个调度.");
        return tr;
    }

    private void autoRedo(TaskLog taskLog){
        try {
            RedoThread redoThread = new RedoThread(taskLog);
            redoThread.setQuartzSource(quartzSource);
            //同步方式执行，否则会导致重复执行
            redoThread.run();
            logger.debug("执行一个调度日志重做线程任务");
        } catch (Exception e) {
            logger.error("自动重做任务，taskLogId="+taskLog.getId()+"异常",e);
        }
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        ParaCheckResult rb = new ParaCheckResult();
        rb.setMessage("");
        para = this.getTriggerParaBean();
        if(para==null){
            rb.setErrorCode(ScheduleErrorCode.TRIGGER_PARA_NULL);
        }
        return rb;
    }

    @Override
    public Class getParaDefine() {
        return AutoRedoJobPara.class;
    }
}
