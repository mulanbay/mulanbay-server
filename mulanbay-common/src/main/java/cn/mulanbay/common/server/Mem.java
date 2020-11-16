package cn.mulanbay.common.server;

import java.io.Serializable;

/**
 * @author fenghong
 * @title: 内存相关信息
 * @date 2020-09-15 22:00
 */
public class Mem implements Serializable {
    private static final long serialVersionUID = 1193726877618770279L;

    /**
     * 总的物理内存
     */
    private long totalMemorySize;

    /**
     * 剩余的物理内存
     */
    private long freePhysicalMemorySize;

    /**
     * 已使用的物理内存
     */
    private long usedMemory;

    public long getTotalMemorySize() {
        return totalMemorySize;
    }

    public void setTotalMemorySize(long totalMemorySize) {
        this.totalMemorySize = totalMemorySize;
    }

    public long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }
}
