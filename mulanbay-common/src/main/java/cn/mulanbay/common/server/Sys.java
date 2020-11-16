package cn.mulanbay.common.server;

import java.io.Serializable;

/**
 * @author fenghong
 * @title: 系统相关信息
 * @create 2020-09-15 22:00
 */
public class Sys implements Serializable {

    private static final long serialVersionUID = 7312965500697516429L;

    /**
     * 操作系统
     */
    private String osName;

    private String serverIp;

    private String userHome;

    private String userName;

    /**
     * 系统架构
     */
    private String osArch;

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getUserHome() {
        return userHome;
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }
}
