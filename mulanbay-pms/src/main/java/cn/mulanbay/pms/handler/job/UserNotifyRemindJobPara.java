package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class UserNotifyRemindJobPara extends AbstractTriggerPara {

    @JobParameter(name = "缓存统计结果", dataType = Boolean.class, desc = "首页显示使用")
    private boolean cacheResult = true;

    @JobParameter(name = "缓存时间", dataType = Integer.class, desc = "秒数(通常等于调度的周期值)", editType = EditType.NUMBER)
    private int expireSeconds = 24 * 3600;

    public boolean getCacheResult() {
        return cacheResult;
    }

    public void setCacheResult(boolean cacheResult) {
        this.cacheResult = cacheResult;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
}
