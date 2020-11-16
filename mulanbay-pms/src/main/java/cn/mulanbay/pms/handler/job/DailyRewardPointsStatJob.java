package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.persistent.dto.UserRewardPointsStat;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 积分统计
 * 每天定时昨天的所得积分
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class DailyRewardPointsStatJob extends AbstractBaseJob {

    AuthService authService;

    PmsNotifyHandler pmsNotifyHandler = null;

    DailyRewardPointsStatJobPara para;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        authService = BeanFactoryUtil.getBean(AuthService.class);
        pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);

        Date startTime = this.getBussDay();
        Date endTime = DateUtil.getTodayTillMiddleNightDate(startTime);
        List<UserRewardPointsStat> list = authService.statUserRewardPoints(startTime, endTime, null);
        if (list.isEmpty()) {
            tr.setComment("今天没人获得积分");
        } else {
            for (UserRewardPointsStat urp : list) {
                handleRewardPointsStat(startTime, urp);
            }
            tr.setExecuteResult(JobExecuteResult.SUCCESS);
            tr.setComment("今天一共" + list.size() + "个人获得积分");
        }
        return tr;
    }

    /**
     * 处理积分统计
     *
     * @param startTime
     * @param urp
     */
    private void handleRewardPointsStat(Date startTime, UserRewardPointsStat urp) {
        String title = "积分奖励统计";
        StringBuffer sb = new StringBuffer();
        String dateStr = DateUtil.getFormatDate(startTime, DateUtil.FormatDay1);
        sb.append("您在" + dateStr + "获取");
        int n = urp.getTotalCount().intValue();
        sb.append(n + "次积分奖励/惩罚");
        if (n > 0) {
            sb.append(",最终获得积分:" + urp.getTotalPoints().longValue() + ".");
        }
        Integer currentPoints = authService.getUserPoint(urp.getUserId().longValue());
        urp.setCurrentPoints(new BigDecimal(currentPoints));
        sb.append("当前总积分为:" + currentPoints);
        Date nextRemindTime = DateUtil.getDate(DateUtil.getToday() + " " + para.getRemindTime() + ":00", DateUtil.Format24Datetime);
        pmsNotifyHandler.addNotifyMessage(PmsErrorCode.REWARD_POINTS_DAILY_STAT, title, sb.toString(),
                urp.getUserId().longValue(), nextRemindTime);
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        para = this.getTriggerParaBean();
        if (para == null) {
            para = new DailyRewardPointsStatJobPara();
        }
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return DailyRewardPointsStatJobPara.class;
    }
}
