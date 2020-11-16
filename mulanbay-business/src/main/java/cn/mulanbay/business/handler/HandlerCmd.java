package cn.mulanbay.business.handler;

/**
 * @author fenghong
 * @title: 命令处理
 * @description: TODO
 * @date 2019/7/10 2:48 PM
 */
public class HandlerCmd {

    private String cmd;

    private String name;

    public HandlerCmd(String cmd, String name) {
        this.cmd = cmd;
        this.name = name;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
