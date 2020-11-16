package cn.mulanbay.pms.web.bean.response.thread;

import java.util.Date;

public class ThreadVo {

    private long id;
    // 线程名称
    private String threadName;

    private boolean isStop;

    // 如果是周期性线程，设置是先sleep再做业务还是先业务再sleep
    private boolean isBeforeSleep;

    private long interval;

    private Date created;

    private Date lastExecuteTime;

    // 总执行次数
    private long totalCount;

    // 总执行失败次数
    private long failCount;

    // 持续运行时间（秒）
    private long continuedSeconds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public boolean isBeforeSleep() {
        return isBeforeSleep;
    }

    public void setBeforeSleep(boolean beforeSleep) {
        isBeforeSleep = beforeSleep;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getFailCount() {
        return failCount;
    }

    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }

    public long getContinuedSeconds() {
        return continuedSeconds;
    }

    public void setContinuedSeconds(long continuedSeconds) {
        this.continuedSeconds = continuedSeconds;
    }
}
