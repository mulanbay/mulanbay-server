package cn.mulanbay.common.server;

import java.io.Serializable;

/**
 * @title: cpu信息
 * @author: fenghong
 * @create 2020-09-15 22:00
 */
public class Cpu implements Serializable {

    private static final long serialVersionUID = -2557426116319851168L;

    //物理cpu核数
    private int physicalProcessorCount;

    //逻辑cpu核数
    private int logicalProcessorCount;

    //cpu总核数
    private long totalCpu;

    //cpu系统使用率
    private double sysCpuRate;

    //cpu用户使用率
    private double userCpuRate;

    //cpu当前等待率
    private double iowaitCpuRate;

    //cpu当前空闲率
    private double idleCpuRate;

    public int getPhysicalProcessorCount() {
        return physicalProcessorCount;
    }

    public void setPhysicalProcessorCount(int physicalProcessorCount) {
        this.physicalProcessorCount = physicalProcessorCount;
    }

    public int getLogicalProcessorCount() {
        return logicalProcessorCount;
    }

    public void setLogicalProcessorCount(int logicalProcessorCount) {
        this.logicalProcessorCount = logicalProcessorCount;
    }

    public long getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(long totalCpu) {
        this.totalCpu = totalCpu;
    }

    public double getSysCpuRate() {
        return sysCpuRate;
    }

    public void setSysCpuRate(double sysCpuRate) {
        this.sysCpuRate = sysCpuRate;
    }

    public double getUserCpuRate() {
        return userCpuRate;
    }

    public void setUserCpuRate(double userCpuRate) {
        this.userCpuRate = userCpuRate;
    }

    public double getIowaitCpuRate() {
        return iowaitCpuRate;
    }

    public void setIowaitCpuRate(double iowaitCpuRate) {
        this.iowaitCpuRate = iowaitCpuRate;
    }

    public double getIdleCpuRate() {
        return idleCpuRate;
    }

    public void setIdleCpuRate(double idleCpuRate) {
        this.idleCpuRate = idleCpuRate;
    }
}
