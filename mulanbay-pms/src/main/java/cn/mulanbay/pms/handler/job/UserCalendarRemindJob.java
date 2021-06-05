package cn.mulanbay.pms.handler.job;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.queue.LimitQueue;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.bean.UserCalendarCountsBean;
import cn.mulanbay.pms.persistent.domain.UserCalendar;
import cn.mulanbay.pms.persistent.service.UserCalendarService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2018-03-13 21:12
 */
public class UserCalendarRemindJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(UserCalendarRemindJob.class);

    private UserCalendarRemindJobPara para;

    @Override
    public TaskResult doTask() {
        TaskResult taskResult = new TaskResult();
        UserCalendarService userCalendarService = BeanFactoryUtil.getBean(UserCalendarService.class);
        List<UserCalendar> list = userCalendarService.getCurrentUserCalendarList(null);
        if (list.isEmpty()) {
            taskResult.setComment("没有用户日历数据");
        } else {
            taskResult.setExecuteResult(JobExecuteResult.SUCCESS);
            PmsNotifyHandler pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
            int n = list.size();
            long currentUserId = list.get(0).getUserId();
            StringBuffer sb = new StringBuffer();
            int aa = 0;
            int tmpIndex = 1;
            for (int i = 0; i < n; i++) {
                UserCalendar uc = list.get(i);
                if (uc.getUserId() != currentUserId) {
                    pmsNotifyHandler.addNotifyMessage(PmsErrorCode.USER_DAILY_TASK_STAT, "今日任务", sb.toString(),
                            currentUserId, null);
                    setCacheCounts(currentUserId, tmpIndex);
                    aa++;
                    currentUserId = uc.getUserId();
                    tmpIndex = 1;
                    sb = new StringBuffer();
                    sb.append((tmpIndex++) + "." + uc.getTitle() + "\n");
                } else {
                    if (i == (n - 1)) {
                        //最后一次
                        sb.append((tmpIndex++) + "." + uc.getTitle() + "\n");
                        pmsNotifyHandler.addNotifyMessage(PmsErrorCode.USER_DAILY_TASK_STAT, "今日任务", sb.toString(),
                                currentUserId, null);
                        setCacheCounts(currentUserId, tmpIndex);
                        aa++;
                    } else {
                        sb.append((tmpIndex++) + "." + uc.getTitle() + "\n");
                    }
                }

            }
            taskResult.setComment("共发送" + aa + "条用户日历数据");
        }
        return taskResult;
    }

    /**
     * 设置缓存次数（供首页使用）
     *
     * @param userId
     * @param counts
     */
    private void setCacheCounts(Long userId, int counts) {
        try {
            CacheHandler cacheHandler = BeanFactoryUtil.getBean(CacheHandler.class);
            //首页右上角的今日任务数使用
            String tk = CacheKey.getKey(CacheKey.USER_TODAY_CALENDAR_COUNTS, userId.toString());
            cacheHandler.set(tk, counts + 1, 24 * 3600);
            //用户日历页面的统计功能使用，查询每日的任务数
            String ttmk = CacheKey.getKey(CacheKey.USER_TODAY_CALENDAR_TIMELINE_COUNTS, userId.toString());
            int expireSeconds = para.getDays() * 24 * 3600;
            LimitQueue<UserCalendarCountsBean> queue = cacheHandler.get(ttmk, LimitQueue.class);
            if (queue == null) {
                queue = new LimitQueue<UserCalendarCountsBean>(para.getQueueSize());
            }
            UserCalendarCountsBean ucc = new UserCalendarCountsBean();
            ucc.setDate(new Date());
            ucc.setCounts(counts);
            queue.offer(ucc);
            cacheHandler.set(ttmk, queue, expireSeconds);
        } catch (Exception e) {
            logger.error("设置缓存次数异常", e);
        }
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        para = this.getTriggerParaBean();
        if (para == null) {
            para = new UserCalendarRemindJobPara();
        }
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return null;
    }
}
