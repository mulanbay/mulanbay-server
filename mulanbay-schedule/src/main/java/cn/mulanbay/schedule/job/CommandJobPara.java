package cn.mulanbay.schedule.job;

import cn.mulanbay.common.config.OSType;
import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class CommandJobPara extends AbstractTriggerPara {

    @JobParameter(name = "命令",dataType = String.class,desc = "绝对路径",editType = EditType.TEXT)
    private String cmd;

    @JobParameter(name = "操作系统类型",
            editType = EditType.COMBOBOX,
            editData = "[{\"id\":\"UNKNOWN\",\"text\":\"由程序判断\"},{\"id\":\"LINUX\",\"text\":\"LINUX\"},{\"id\":\"WINDOWS\",\"text\":\"WINDOWS\"}]",
            dataType = Integer.class)
    private OSType osType;

    @JobParameter(name = "是否同步",editType = EditType.BOOLEAN,dataType = Boolean.class)
    private boolean asyn = false;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public OSType getOsType() {
        return osType;
    }

    public void setOsType(OSType osType) {
        this.osType = osType;
    }

    public boolean isAsyn() {
        return asyn;
    }

    public void setAsyn(boolean asyn) {
        this.asyn = asyn;
    }
}
