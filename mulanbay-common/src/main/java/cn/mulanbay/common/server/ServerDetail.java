package cn.mulanbay.common.server;

import cn.mulanbay.common.util.IPAddressUtil;
import com.sun.management.OperatingSystemMXBean;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @Description: 系统详情
 * @author: fenghong
 * @create 2020-09-15 22:00
 * @see {https://www.cnblogs.com/rvs-2016/p/11169894.html}
 */
public class ServerDetail implements Serializable {

    private static final long serialVersionUID = 1429846767427613876L;

    private static final int OSHI_WAIT_SECOND = 1000;

    /**
     * 采集时间
     */
    private Date date;

    /**
     * CPU相关信息
     */
    private Cpu cpu = new Cpu();

    /**
     * 內存相关信息
     */
    private Mem mem = new Mem();

    /**
     * JVM相关信息
     */
    private Jvm jvm = new Jvm();

    /**
     * 服务器相关信息
     */
    private Sys sys = new Sys();

    /**
     * 磁盘相关信息
     */
    private List<SysFile> sysFiles = new LinkedList<SysFile>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Mem getMem() {
        return mem;
    }

    public void setMem(Mem mem) {
        this.mem = mem;
    }

    public Jvm getJvm() {
        return jvm;
    }

    public void setJvm(Jvm jvm) {
        this.jvm = jvm;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<SysFile> getSysFiles() {
        return sysFiles;
    }

    public void setSysFiles(List<SysFile> sysFiles) {
        this.sysFiles = sysFiles;
    }

    /**
     * 复制信息
     *
     * @throws Exception
     */
    public void copyTo() {
        SystemInfo si = new SystemInfo();
        this.date = new Date();
        HardwareAbstractionLayer hal = si.getHardware();
        setCpuInfo(hal.getProcessor());
        setMemInfo(hal.getMemory());
        setSysInfo();
        setJvmInfo();
        setSysFiles(si.getOperatingSystem());
    }

    /**
     * 设置CPU信息
     */
    private void setCpuInfo(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setPhysicalProcessorCount(processor.getPhysicalProcessorCount());
        cpu.setLogicalProcessorCount(processor.getLogicalProcessorCount());
        if (totalCpu == 0) {
            totalCpu = cpu.getPhysicalProcessorCount();
        }
        cpu.setTotalCpu(totalCpu);
        cpu.setSysCpuRate(cSys * 1.0 / totalCpu);
        cpu.setUserCpuRate(user * 1.0 / totalCpu);
        cpu.setIowaitCpuRate(iowait * 1.0 / totalCpu);
        cpu.setIdleCpuRate(idle * 1.0 / totalCpu);
    }

    /**
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory)
    {
        mem.setTotalMemorySize(memory.getTotal());
        mem.setUsedMemory(memory.getTotal() - memory.getAvailable());
        mem.setFreePhysicalMemorySize(memory.getAvailable());
    }

    /**
     * 设置服务器信息
     */
    private void setSysInfo()
    {
        Properties props = System.getProperties();
        sys.setOsName(System.getProperty("os.name"));
        sys.setServerIp(IPAddressUtil.getLocalIpAddress());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserHome(System.getProperty("user.home"));
        sys.setUserName(System.getProperty("user.name"));
    }

    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo()
    {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 椎内存使用情况
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

        jvm.setInitTotalMemorySize(memoryUsage.getInit());
        jvm.setMaxMemorySize(memoryUsage.getMax());
        jvm.setUsedMemorySize(memoryUsage.getUsed());
        jvm.setTotalMemorySize(osmxb.getTotalPhysicalMemorySize());
        // 获得线程总数
        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                .getParent() != null; parentThread = parentThread.getParent()) {
        }
        jvm.setStartTime(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));
        jvm.setPid(System.getProperty("PID"));
        int totalThread = parentThread.activeCount();
        jvm.setTotalThread(totalThread);
        jvm.setJavaHome(System.getProperty("java.home"));
        jvm.setJavaVersion(System.getProperty("java.version"));
    }

    /**
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os)
    {
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray)
        {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setPath(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotalSpace(total);
            sysFile.setFreeSpace(free);
            sysFiles.add(sysFile);
        }
    }


}
