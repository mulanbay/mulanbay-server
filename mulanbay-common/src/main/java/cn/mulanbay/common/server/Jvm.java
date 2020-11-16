package cn.mulanbay.common.server;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fenghong
 * @title: jvm信息
 * @date 2020-09-15 22:00
 */
public class Jvm implements Serializable {

    private static final long serialVersionUID = -7396267411371926690L;

    /**
     * 程序启动时间
     */
    private Date startTime;

    private String pid;

    private String javaHome;

    private String javaVersion;

    /**
     * 总线程数
     */
    private int totalThread;

    /**
     * 当前JVM占用的内存总数(M)
     */
    private double totalMemorySize;

    /**
     * 初始的总内存(JVM)
     */
    private long initTotalMemorySize;

    /**
     * 最大可用内存(JVM)
     */
    private long maxMemorySize;

    /**
     * 已使用的内存(JVM)
     */
    private long usedMemorySize;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getJavaHome() {
        return javaHome;
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public int getTotalThread() {
        return totalThread;
    }

    public void setTotalThread(int totalThread) {
        this.totalThread = totalThread;
    }

    public double getTotalMemorySize() {
        return totalMemorySize;
    }

    public void setTotalMemorySize(double totalMemorySize) {
        this.totalMemorySize = totalMemorySize;
    }

    public long getInitTotalMemorySize() {
        return initTotalMemorySize;
    }

    public void setInitTotalMemorySize(long initTotalMemorySize) {
        this.initTotalMemorySize = initTotalMemorySize;
    }

    public long getMaxMemorySize() {
        return maxMemorySize;
    }

    public void setMaxMemorySize(long maxMemorySize) {
        this.maxMemorySize = maxMemorySize;
    }

    public long getUsedMemorySize() {
        return usedMemorySize;
    }

    public void setUsedMemorySize(long usedMemorySize) {
        this.usedMemorySize = usedMemorySize;
    }
}
