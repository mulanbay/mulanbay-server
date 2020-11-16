package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.config.OSType;
import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

/**
 * 命令job的参数定义
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class PmsCommandJobPara extends AbstractTriggerPara {

    @JobParameter(name = "命令", dataType = String.class, desc = "绝对路径")
    private String cmd;

    @JobParameter(name = "操作系统", editType = EditType.COMBOBOX,
            editData = "[{\"id\":\"UNKNOWN\",\"text\":\"由程序判断\"},{\"id\":\"LINUX\",\"text\":\"LINUX\"},{\"id\":\"WINDOWS\",\"text\":\"WINDOWS\"}]",
            dataType = Integer.class)
    private OSType osType;

    @JobParameter(name = "是否同步", editType = EditType.BOOLEAN, dataType = Boolean.class)
    private boolean asyn = false;

    @JobParameter(name = "代码", editType = EditType.TEXT, dataType = String.class, desc = "配置了命令直接使用命令，否则用命令代码表配置")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
