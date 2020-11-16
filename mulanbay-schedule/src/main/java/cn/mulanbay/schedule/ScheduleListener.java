package cn.mulanbay.schedule;

import cn.mulanbay.schedule.domain.TaskTrigger;

/**
 * @author fenghong
 * @title: TriggerChangeListener
 * @description: 调度触发器改变的监听器
 * @date 2019-11-15 21:14
 */
public interface ScheduleListener {

    /**
     * 修改全部
     */
    public void changeAll();

    /**
     * 修改单独一个
     * @param tt
     */
    public void change(TaskTrigger tt);

    public void destroy();
}
