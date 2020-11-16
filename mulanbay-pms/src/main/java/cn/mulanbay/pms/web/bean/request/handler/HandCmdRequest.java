package cn.mulanbay.pms.web.bean.request.handler;

import javax.validation.constraints.NotEmpty;

public class HandCmdRequest {

    @NotEmpty(message = "{validate.handler.className.NotEmpty}")
    private String className;

    @NotEmpty(message = "{validate.handler.cmd.NotEmpty}")
    private String cmd;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
