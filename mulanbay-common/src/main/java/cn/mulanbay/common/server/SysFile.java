package cn.mulanbay.common.server;

import java.io.Serializable;

/**
 * @author fenghong
 * @title: 系统文件信息
 * @create 2020-09-15 22:00
 */
public class SysFile implements Serializable {
    private static final long serialVersionUID = -4751474536959996874L;

    /**
     * 磁盘路径
     */
    private String path;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 文件类型
     */
    private String typeName;

    /**
     * 总空间
     */
    private long totalSpace;

    /**
     * 空闲空间
     */
    private long freeSpace;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSysTypeName() {
        return sysTypeName;
    }

    public void setSysTypeName(String sysTypeName) {
        this.sysTypeName = sysTypeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(long totalSpace) {
        this.totalSpace = totalSpace;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(long freeSpace) {
        this.freeSpace = freeSpace;
    }

}
