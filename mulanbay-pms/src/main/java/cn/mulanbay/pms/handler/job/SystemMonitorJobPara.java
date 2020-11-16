package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class SystemMonitorJobPara extends AbstractTriggerPara {

    @JobParameter(name = "磁盘报警比例", dataType = Double.class, desc = "百分比值:0.01-0.99", editType = EditType.NUMBER, scale = 2)
    private double diskMaxRate = 0.8;

    @JobParameter(name = "内存报警比例", dataType = Double.class, desc = "百分比值:0.01-0.99", editType = EditType.NUMBER, scale = 2)
    private double memoryMaxRate = 0.9;

    @JobParameter(name = "CPU报警比例", dataType = Double.class, desc = "百分比值:0.01-0.99", editType = EditType.NUMBER, scale = 2)
    private double cpuMaxRate = 0.8;

    @JobParameter(name = "监控历史记录保留条数", dataType = Integer.class, desc = "条", editType = EditType.NUMBER)
    private int queueSize = 100;

    @JobParameter(name = "监控历史记录保留天数", dataType = Integer.class, desc = "天", editType = EditType.NUMBER)
    private int days = 7;

    @JobParameter(name = "磁盘报警后的执行调度", dataType = String.class, desc = "调度编号，多个则以英文逗号分隔")
    private String diskAlertSelfJobs;

    @JobParameter(name = "内存报警后的执行调度", dataType = String.class, desc = "调度编号，多个则以英文逗号分隔")
    private String memoryAlertSelfJobs;

    @JobParameter(name = "CPU报警后的执行调度", dataType = String.class, desc = "调度编号，多个则以英文逗号分隔")
    private String cpuAlertSelfJobs;

    @JobParameter(name = "报警后自动执行清理调度", dataType = Boolean.class, desc = "")
    private Boolean autoDoJob = true;

    public double getDiskMaxRate() {
        return diskMaxRate;
    }

    public void setDiskMaxRate(double diskMaxRate) {
        this.diskMaxRate = diskMaxRate;
    }

    public double getMemoryMaxRate() {
        return memoryMaxRate;
    }

    public void setMemoryMaxRate(double memoryMaxRate) {
        this.memoryMaxRate = memoryMaxRate;
    }

    public double getCpuMaxRate() {
        return cpuMaxRate;
    }

    public void setCpuMaxRate(double cpuMaxRate) {
        this.cpuMaxRate = cpuMaxRate;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getDiskAlertSelfJobs() {
        return diskAlertSelfJobs;
    }

    public void setDiskAlertSelfJobs(String diskAlertSelfJobs) {
        this.diskAlertSelfJobs = diskAlertSelfJobs;
    }

    public String getMemoryAlertSelfJobs() {
        return memoryAlertSelfJobs;
    }

    public void setMemoryAlertSelfJobs(String memoryAlertSelfJobs) {
        this.memoryAlertSelfJobs = memoryAlertSelfJobs;
    }

    public String getCpuAlertSelfJobs() {
        return cpuAlertSelfJobs;
    }

    public void setCpuAlertSelfJobs(String cpuAlertSelfJobs) {
        this.cpuAlertSelfJobs = cpuAlertSelfJobs;
    }

    public Boolean getAutoDoJob() {
        return autoDoJob;
    }

    public void setAutoDoJob(Boolean autoDoJob) {
        this.autoDoJob = autoDoJob;
    }
}
