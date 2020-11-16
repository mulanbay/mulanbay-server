package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.pms.persistent.service.UserCalendarService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;

import java.util.Date;

/**
 * 用户日历超时设置任务
 */
public class UserCalendarTimeoutJob extends AbstractBaseJob {

    @Override
    public TaskResult doTask() {
        TaskResult taskResult = new TaskResult();
        UserCalendarService userCalendarService = BeanFactoryUtil.getBean(UserCalendarService.class);
        int n = userCalendarService.updateUserCalendarForExpired(new Date());
        taskResult.setComment("更新了" + n + "个超时用户日历");
        taskResult.setExecuteResult(JobExecuteResult.SUCCESS);
        return taskResult;
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return null;
    }
}
