package cn.mulanbay.schedule;


import cn.mulanbay.schedule.lock.ScheduleLocker;

/**
 * 调度的资源
 *
 * @author fenghong
 * @create 2017-11-11 17:14
 **/
public class QuartzSource {

    /**
     *  分布式任务最小的花费时间(秒数)
     */
    private int distriTaskMinCost=2;

    /**
     * 部署点
     */
    private String deployId;

    /**
     * 是否支持分布式
     */
    private boolean supportDistri=false;

    /**
     * 分布式锁
     */
    private ScheduleLocker scheduleLocker;

    /**
     * 持久层操作
     */
    private SchedulePersistentProcessor schedulePersistentProcessor;

    /**
     * 通知类（调度执行失败的时候传入）
     */
    private NotifiableProcessor notifiableProcessor;

    public int getDistriTaskMinCost() {
        return distriTaskMinCost;
    }

    public void setDistriTaskMinCost(int distriTaskMinCost) {
        this.distriTaskMinCost = distriTaskMinCost;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public boolean isSupportDistri() {
        return supportDistri;
    }

    public void setSupportDistri(boolean supportDistri) {
        this.supportDistri = supportDistri;
    }

    public ScheduleLocker getScheduleLocker() {
        return scheduleLocker;
    }

    public void setScheduleLocker(ScheduleLocker scheduleLocker) {
        this.scheduleLocker = scheduleLocker;
    }

    public SchedulePersistentProcessor getSchedulePersistentProcessor() {
        return schedulePersistentProcessor;
    }

    public void setSchedulePersistentProcessor(SchedulePersistentProcessor schedulePersistentProcessor) {
        this.schedulePersistentProcessor = schedulePersistentProcessor;
    }

    public NotifiableProcessor getNotifiableProcessor() {
        return notifiableProcessor;
    }

    public void setNotifiableProcessor(NotifiableProcessor notifiableProcessor) {
        this.notifiableProcessor = notifiableProcessor;
    }
}
