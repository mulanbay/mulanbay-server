package cn.mulanbay.pms.web.bean.response.schedule;

import cn.mulanbay.schedule.domain.TaskTrigger;

import java.util.Date;

public class TaskTriggerVo extends TaskTrigger {

    /**
     * 是否正在被执行
     */
    private boolean executing;

    /**
     * 距离下一次执行时间(秒)
     *
     * @return
     */
    public Long getTillNextExecuteTime() {
        Date dd = this.getNextExecuteTime();
        if (dd == null) {
            dd = this.getFirstExecuteTime();
        }
        return (dd.getTime() - System.currentTimeMillis()) / 1000;
    }

    public String getRedoTypeName() {
        return this.getRedoType() == null ? null : this.getRedoType().getName();
    }

    public String getTriggerTypeName() {
        return this.getTriggerType() == null ? null : this.getTriggerType().getName();
    }

    @Override
    public String getLastExecuteResultName() {
        return this.getLastExecuteResult() == null ? null : this.getLastExecuteResult().getName();
    }

    public String getUniqueTypeName() {
        return this.getUniqueType() == null ? null : this.getUniqueType().getName();
    }

    public boolean isExecuting() {
        return executing;
    }

    public void setExecuting(boolean executing) {
        this.executing = executing;
    }
}
