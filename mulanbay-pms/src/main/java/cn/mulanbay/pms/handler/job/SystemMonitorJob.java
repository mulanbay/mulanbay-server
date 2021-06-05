package cn.mulanbay.pms.handler.job;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.queue.LimitQueue;
import cn.mulanbay.common.server.Cpu;
import cn.mulanbay.common.server.Mem;
import cn.mulanbay.common.server.ServerDetail;
import cn.mulanbay.common.server.SysFile;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.PmsScheduleHandler;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author fenghong
 * @title: 系统监控
 * @description: TODO
 * @date 2019/4/24 12:10 PM
 */
public class SystemMonitorJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(SystemMonitorJob.class);

    private SystemMonitorJobPara para;

    PmsNotifyHandler pmsNotifyHandler;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
        ServerDetail sd = new ServerDetail();
        sd.copyTo();
        SysFile di = sd.getSysFiles().get(0);
        double diskRate = (di.getTotalSpace() - di.getFreeSpace()) * 1.0 / di.getTotalSpace();
        int n = 0;
        if (diskRate >= para.getDiskMaxRate()) {
            String content = "磁盘[" + di.getPath() + "]报警，使用率达到" + PriceUtil.changeToString(2, diskRate * 100) +
                    "%,超过系统警告值:" + PriceUtil.changeToString(2, para.getDiskMaxRate() * 100) + "%";
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.DISK_ALERT, "磁盘报警", content, new Date(), null);
            doAlertAutoJobs(para.getDiskAlertSelfJobs());
            n++;
        }
        Mem mem = sd.getMem();
        double memoryRate = (mem.getTotalMemorySize() - mem.getFreePhysicalMemorySize()) * 1.0 / mem.getTotalMemorySize();
        if (memoryRate >= para.getMemoryMaxRate()) {
            String content = "内存报警，使用率达到" + PriceUtil.changeToString(2, memoryRate * 100) +
                    "%,超过系统警告值:" + PriceUtil.changeToString(2, para.getMemoryMaxRate() * 100) + "%";
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.MEMORY_ALERT, "内存报警", content, new Date(), null);
            doAlertAutoJobs(para.getMemoryAlertSelfJobs());
            n++;
        }
        Cpu cpu = sd.getCpu();
        double cpuRate = cpu.getSysCpuRate();
        if (cpuRate >= para.getCpuMaxRate()) {
            String content = "CPU报警，使用率达到" + PriceUtil.changeToString(2, cpuRate * 100) +
                    "%,超过系统警告值:" + PriceUtil.changeToString(2, para.getCpuMaxRate() * 100) + "%";
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.CPU_ALERT, "CPU报警", content, new Date(), null);
            doAlertAutoJobs(para.getCpuAlertSelfJobs());
            n++;
        }
        addTimeline(sd);
        if (n == 0) {
            tr.setExecuteResult(JobExecuteResult.SKIP);
        } else {
            tr.setExecuteResult(JobExecuteResult.SUCCESS);
            tr.setComment("一共发送了" + n + "个报警提醒");
        }
        return tr;
    }

    /**
     * 系统报警后执行自动执行任务
     * 任务需要不检查唯一性
     *
     * @param jobs
     */
    private void doAlertAutoJobs(String jobs) {
        if (false == para.getAutoDoJob()) {
            logger.warn("系统配置报警后不需要执行自动执行的任务");
            return;
        }
        if (StringUtil.isEmpty(jobs)) {
            logger.warn("未配置系统报警后执行自动执行的任务");
            return;
        }
        PmsScheduleHandler pmsScheduleHandler = BeanFactoryUtil.getBean(PmsScheduleHandler.class);
        boolean isSchedule = pmsScheduleHandler.isEnableSchedule();
        if (!isSchedule) {
            logger.warn("该服务器未启动调度服务，无法执行系统报警后执行自动任务");
        }
        //避免误写分隔符
        jobs = jobs.replace("，", ",");
        String[] ids = jobs.split(",");
        for (String s : ids) {
            try {
                pmsScheduleHandler.manualNew(Long.valueOf(s), DateUtil.getDate(0), true, null, null);
                String content = "系统报警后执行自动执行任务,任务编号:" + s;
                pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.SYSTEM_ALERT_AUTO_JOB, "系统报警后执行自动执行任务", content, new Date(), "系统报警后执行自动执行任务");
            } catch (Exception e) {
                logger.error("系统报警后执行自动执行任务,id=" + s + "异常", e);
            }
        }
    }

    private void addTimeline(ServerDetail sd) {
        try {
            CacheHandler cacheHandler = BeanFactoryUtil.getBean(CacheHandler.class);
            int expireSeconds = para.getDays() * 24 * 3600;
            String key = CacheKey.getKey(CacheKey.SERVER_DETAIL_MONITOR_TIMELINE);
            LimitQueue<ServerDetail> queue = cacheHandler.get(key, LimitQueue.class);
            if (queue == null) {
                queue = new LimitQueue<ServerDetail>(para.getQueueSize());
            }
            sd.setDate(new Date());
            queue.offer(sd);
            cacheHandler.set(key, queue, expireSeconds);
        } catch (Exception e) {
            logger.error("保存系统监控时间线记录异常", e);
        }
    }


    @Override
    public ParaCheckResult checkTriggerPara() {
        para = this.getTriggerParaBean();
        if (para == null) {
            para = new SystemMonitorJobPara();
        }
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return SystemMonitorJobPara.class;
    }
}
