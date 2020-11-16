package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

/**
 * 文件同步的参数定义
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class FtpSyncToRemoteJobPara extends AbstractTriggerPara {

    @JobParameter(name = "远程服务器地址", dataType = String.class)
    private String serverHost;

    @JobParameter(name = "登录用户名", dataType = String.class)
    private String username;

    @JobParameter(name = "登录密码", dataType = String.class)
    private String password;

    @JobParameter(name = "端口号", dataType = Integer.class, desc = "值：21", editType = EditType.NUMBER)
    private int port = 21;

    /**
     * 远程ftp目录
     */
    @JobParameter(name = "远程服务器的目录", dataType = String.class)
    private String remotePath;

    /**
     * 本地需要同步的目录
     */
    @JobParameter(name = "本地需要同步的目录", dataType = String.class)
    private String localPath;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
