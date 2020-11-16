package cn.mulanbay.schedule.impl;

import cn.mulanbay.schedule.ScheduleListener;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.schedule.handler.ScheduleHandler;
import cn.mulanbay.schedule.thread.QuartzMonitorThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fenghong
 * @title: SimpleTriggerChangeListener
 * @description: 简单实现，基于单机版本
 * @date 2019-11-15 21:45
 */
public class SimpleScheduleListener implements ScheduleListener {

    private static final Logger logger = LoggerFactory.getLogger(SimpleScheduleListener.class);

    QuartzMonitorThread monitorThread;

    ScheduleHandler scheduleHandler;

    public void listen(){
        monitorThread = new QuartzMonitorThread(scheduleHandler);
        monitorThread.start();
        logger.debug("启动调度监控服务");
    }

    @Override
    public void changeAll() {
        scheduleHandler.checkAndRefreshSchedule(false);
    }

    @Override
    public void change(TaskTrigger tt) {
        scheduleHandler.refreshTask(tt);
    }

    @Override
    public void destroy() {
        monitorThread.stopThread();
    }

}
