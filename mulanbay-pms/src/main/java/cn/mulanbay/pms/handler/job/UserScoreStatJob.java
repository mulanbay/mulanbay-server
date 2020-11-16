package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.pms.handler.UserScoreHandler;
import cn.mulanbay.pms.persistent.service.UserScoreService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @author fenghong
 * @title: UserScoreStatJob
 * @description: 用户评分统计
 * @date 2019-09-09 17:20
 */
public class UserScoreStatJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(UserScoreStatJob.class);

    UserScoreService userScoreService;

    UserScoreHandler userScoreHandler;

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        userScoreService = BeanFactoryUtil.getBean(UserScoreService.class);
        userScoreHandler = BeanFactoryUtil.getBean(UserScoreHandler.class);
        Date date = this.getBussDay();
        Date[] dates = userScoreHandler.getDays(date);
        List<Long> userIdList = userScoreService.selectNeedStatScoreUserIdList();
        int success = 0;
        int fail = 0;
        for (Long userId : userIdList) {
            boolean b = statScore(userId, dates[0], dates[1]);
        }
        result.setExecuteResult(JobExecuteResult.SUCCESS);
        result.setComment("一共统计了" + userIdList.size() + "个用户积分,成功:" + success + "个,失败" + fail + "个");
        return result;
    }

    private boolean statScore(Long userId, Date startTime, Date endTime) {
        try {
            userScoreHandler.saveUseScore(userId, startTime, endTime, this.isRedo());
            return true;
        } catch (Exception e) {
            logger.error("保存用户userId=" + userId + "的积分异常", e);
            return false;
        }
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
