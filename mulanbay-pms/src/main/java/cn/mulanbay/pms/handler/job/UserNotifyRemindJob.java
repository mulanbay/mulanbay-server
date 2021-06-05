package cn.mulanbay.pms.handler.job;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.ConfigKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.NotifyStatHandler;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.persistent.domain.UserCalendar;
import cn.mulanbay.pms.persistent.domain.UserNotify;
import cn.mulanbay.pms.persistent.domain.UserNotifyRemind;
import cn.mulanbay.pms.persistent.dto.NotifyResult;
import cn.mulanbay.pms.persistent.enums.ResultType;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.persistent.enums.UserCalendarFinishType;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;
import cn.mulanbay.pms.persistent.service.NotifyService;
import cn.mulanbay.pms.persistent.service.UserCalendarService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.enums.TriggerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 统计用户提醒的调度
 * 如果达到警告、告警值，往消息表写一条待发送记录
 * 一般为每天凌晨统计，根据用户配置的提醒时间设置expectSendTime值
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class UserNotifyRemindJob extends AbstractBaseRemindJob {

    private static final Logger logger = LoggerFactory.getLogger(UserCalendarRemindJob.class);

    private UserNotifyRemindJobPara para;

    NotifyService notifyService;

    CacheHandler cacheHandler;

    PmsNotifyHandler pmsNotifyHandler = null;

    RewardPointsHandler rewardPointsHandler = null;

    NotifyStatHandler notifyStatHandler = null;

    UserCalendarService userCalendarService = null;

    BaseService baseService = null;

    @Override
    public TaskResult doTask() {
        TaskResult taskResult = new TaskResult();
        notifyService = BeanFactoryUtil.getBean(NotifyService.class);
        List<UserNotify> list = notifyService.getNeedRemindUserNotify();
        cacheHandler = BeanFactoryUtil.getBean(CacheHandler.class);
        pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
        rewardPointsHandler = BeanFactoryUtil.getBean(RewardPointsHandler.class);
        notifyStatHandler = BeanFactoryUtil.getBean(NotifyStatHandler.class);
        userCalendarService = BeanFactoryUtil.getBean(UserCalendarService.class);
        baseService = BeanFactoryUtil.getBean(BaseService.class);
        if (list.isEmpty()) {
            taskResult.setExecuteResult(JobExecuteResult.SUCCESS);
            taskResult.setComment("没有需要提醒的用户提醒");
        } else {
            int success = 0;
            int fail = 0;
            for (UserNotify userNotify : list) {
                if(userNotify.getUserId()== ConfigKey.DEFAULT_ADMIN_USER){
                    //默认的管理员用户不参与统计，只做模板使用
                    continue;
                }
                boolean b = handleUserNotify(userNotify);
                if (b) {
                    success++;
                } else {
                    fail++;
                }
            }
            taskResult.setExecuteResult(JobExecuteResult.SUCCESS);
            taskResult.setComment("一共统计了" + list.size() + "个用户提醒,成功:" + success + "个,失败" + fail + "个");
        }
        return taskResult;
    }

    private boolean handleUserNotify(UserNotify userNotify) {
        try {
            if (!userNotify.getRemind()) {
                return true;
            }
            Long userId = userNotify.getUserId();
            NotifyResult notifyResult = null;
            if (para.getCacheResult()) {
                //如果设置需要缓存，那么第一步先去缓存结果
                notifyResult = notifyService.getNotifyResult(userNotify, userId);
                notifyStatHandler.cacheNotifyResult(notifyResult, para.getExpireSeconds());
            }
            //Step 1: 第一步先判断是否已经通知过
            String key = CacheKey.getKey(CacheKey.USER_NOTIFY, userId.toString(), userNotify.getId().toString());
            String cs = cacheHandler.getForString(key);
            if (cs != null) {
                logger.debug("用户ID=" + userId + "的提醒[" + userNotify.getTitle() + "],id=" + userNotify.getId() + "已经提醒过了");
                return true;
            }
            //Step 2: 通知
            UserNotifyRemind unr = notifyService.getUserNotifyRemind(userNotify.getId(), userNotify.getUserId());
            if (unr == null) {
                //初始化一个
                unr = new UserNotifyRemind();
                unr.setCreatedTime(new Date());
                unr.setOverAlertRemind(true);
                unr.setOverWarningRemind(true);
                unr.setRemark("由调度系统自动生成");
                unr.setRemindTime("08:30");
                unr.setTriggerInterval(1);
                unr.setTriggerType(TriggerType.DAY);
                unr.setUserId(userId);
                unr.setUserNotify(userNotify);
                baseService.saveObject(unr);
            }
            //Step 2: 通知
            if (notifyResult == null) {
                notifyResult = notifyService.getNotifyResult(userNotify, userId);
            }
            String title = null;
            String content = null;
            if (notifyResult.getOverAlertValue() > 0 && unr.getOverAlertRemind()) {
                // 达到报警值提醒
                title = "[" + userNotify.getTitle() + "]报警";
                if (userNotify.getNotifyConfig().getResultType() == ResultType.NAME_DATE || userNotify.getNotifyConfig().getResultType() == ResultType.NAME_NUMBER) {
                    content = "[" + userNotify.getTitle() + "][" + notifyResult.getName() + "]超过报警值[" + userNotify.getAlertValue() + "],实际值为["
                            + notifyResult.getCompareValue() + "],计量单位:[" + userNotify.getNotifyConfig().getValueTypeName() + "]\n";
                } else {
                    content = "[" + userNotify.getTitle() + "]超过报警值[" + userNotify.getAlertValue() + "],实际值为["
                            + notifyResult.getCompareValue() + "],计量单位:[" + userNotify.getNotifyConfig().getValueTypeName() + "]\n";
                }
                notifyMessage(title, content, unr, userId, userNotify.getId());
            } else if (notifyResult.getOverWarningValue() > 0 && unr.getOverWarningRemind()) {
                // 达到警告值提醒
                title = "[" + userNotify.getTitle() + "]警告";
                if (userNotify.getNotifyConfig().getResultType() == ResultType.NAME_DATE || userNotify.getNotifyConfig().getResultType() == ResultType.NAME_NUMBER) {
                    content = "[" + userNotify.getTitle() + "][" + notifyResult.getName() + "]超过警告值[" + userNotify.getWarningValue() + "],实际值为["
                            + notifyResult.getCompareValue() + "],计量单位:[" + userNotify.getNotifyConfig().getValueTypeName() + "]\n";
                } else {
                    content = "[" + userNotify.getTitle() + "]超过警告值[" + userNotify.getWarningValue() + "],实际值为["
                            + notifyResult.getCompareValue() + "],计量单位:[" + userNotify.getNotifyConfig().getValueTypeName() + "]\n";
                }
                notifyMessage(title, content, unr, userId, userNotify.getId());
            } else {
                logger.debug("用户ID=" + userId + "的提醒[" + userNotify.getTitle() + "],id=" + userNotify.getId() + "不需要提醒");
                rewardPoint(userNotify, true, null);
            }
            //Step 3: 不能在这里设置上一次的提醒缓存，参见notifyMessage方法
            //Step 4: 加入缓存(方便用户日历统计)
            //cacheHandler.set("userNotifyResult:"+userId+":"+DateUtil.getFormatDate(this.getBussDay(),DateUtil.FormatDay1),notifyResult,24*3600);
            return true;
        } catch (Exception e) {
            logger.error("处理用户提醒:" + userNotify.getTitle() + "异常", e);
            return false;
        }
    }

    private void notifyMessage(String title, String content, UserNotifyRemind remind, Long userId, Long userNotifyId) {
        content = content + "统计日期:" + DateUtil.getFormatDate(this.getBussDay(), DateUtil.FormatDay1);
        RemindTimeBean bean = this.calcRemindExpectTime(remind.getTriggerInterval(), remind.getTriggerType(), remind.getLastRemindTime(), remind.getRemindTime());
        //Step 1: 发送消息通知
        Long messageId = pmsNotifyHandler.addNotifyMessage(PmsErrorCode.USER_NOTIFY_STAT, title, content,
                userId, bean.getNextRemindTime());
        //Step 2: 更新最后的提醒时间
        notifyService.updateLastRemindTime(remind.getId(), new Date());
        /**
         * Step 3: 设置提醒的缓存，避免下一次重复提醒
         * 需要在这里设置上一次的提醒缓存，不能再handleUserPlanRemind方法里设置
         * 因为只有触发了提醒条件才能告知下一次是否能提醒
         */
        String key = CacheKey.getKey(CacheKey.USER_NOTIFY, userId.toString(), userNotifyId.toString());
        //失效时间为通知周期的秒数，-5为了保证第二次通知时间点job能执行
        cacheHandler.set(key, "123", bean.getDays() * 24 * 3600 - 5);
        //Step 4: 更新积分
        rewardPoint(remind.getUserNotify(), false, messageId);
        //Step 5: 加入用户日历
        addToUserCalendar(remind, messageId);
    }

    /**
     * 更新积分，完成+，未达到要求减
     *
     * @param userNotify
     */
    private void rewardPoint(UserNotify userNotify, boolean isComplete, Long messageId) {
        try {
            int radio = 1;
            int rewards = userNotify.getNotifyConfig().getRewardPoint() * radio;
            String remark = "用户提醒配置[" + userNotify.getTitle() + "]达到要求奖励";
            if (!isComplete) {
                rewards = 0 - rewards;
                remark = "用户提醒配置[" + userNotify.getTitle() + "]触发警报惩罚";
            }
            rewardPointsHandler.rewardPoints(userNotify.getUserId(), rewards, userNotify.getId(), RewardSource.NOTIFY, remark, messageId);
            if (isComplete) {
                String bussKey = userNotify.getNotifyConfig().getBussKey();
                if (StringUtil.isEmpty(bussKey)) {
                    logger.warn(userNotify.getNotifyConfig().getTitle() + "没有配置bussKey");
                    return;
                }
                String bussIdentityKey = bussKey;
                if (!StringUtil.isEmpty(userNotify.getBindValues())) {
                    bussIdentityKey += "_" + userNotify.getBindValues();
                }
                userCalendarService.updateUserCalendarForFinish(userNotify.getUserId(), bussIdentityKey, new Date(), UserCalendarFinishType.AUTO, messageId);
            }
        } catch (Exception e) {
            logger.error("计划[" + userNotify.getTitle() + "]积分奖励异常", e);
        }
    }

    /**
     * 更新到用户日历
     *
     * @param remind
     */
    private void addToUserCalendar(UserNotifyRemind remind, Long messageId) {
        try {
            UserNotify userNotify = remind.getUserNotify();
            String bussKey = userNotify.getNotifyConfig().getBussKey();
            if (StringUtil.isEmpty(bussKey)) {
                logger.warn(userNotify.getNotifyConfig().getTitle() + "没有配置bussKey");
                return;
            }
            String bussIdentityKey = bussKey;
            if (!StringUtil.isEmpty(userNotify.getBindValues())) {
                bussIdentityKey += "_" + userNotify.getBindValues();
            }
            UserCalendar uc = userCalendarService.getUserCalendar(userNotify.getUserId(), bussIdentityKey, new Date());
            if (uc != null) {
                userCalendarService.updateUserCalendarToDate(uc, new Date(), messageId);
            } else {
                uc = new UserCalendar();
                uc.setUserId(userNotify.getUserId());
                uc.setTitle(userNotify.getCalendarTitle());
                uc.setContent(userNotify.getCalendarTitle());
                uc.setDelayCounts(0);
                String calendarTime = userNotify.getCalendarTime();
                if (StringUtil.isEmpty(calendarTime)) {
                    uc.setBussDay(DateUtil.getDate(0));
                    uc.setAllDay(true);
                } else {
                    Date bussDay = DateUtil.addHourMinToDate(null, calendarTime);
                    uc.setBussDay(bussDay);
                    uc.setAllDay(false);
                }
                int rate = 1;
                if (remind.getTriggerType() == TriggerType.MONTH) {
                    rate = 30;
                } else if (remind.getTriggerType() == TriggerType.WEEK) {
                    rate = 7;
                }
                uc.setExpireTime(DateUtil.getDate(remind.getTriggerInterval() * rate));
                uc.setBussIdentityKey(bussIdentityKey);
                uc.setSourceType(UserCalendarSource.NOTIFY);
                uc.setSourceId(userNotify.getId().toString());
                uc.setMessageId(messageId);
                userCalendarService.addUserCalendarToDate(uc);
            }
        } catch (Exception e) {
            logger.error("添加到用户日历异常", e);
        }

    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        para = this.getTriggerParaBean();
        if (para == null) {
            para = new UserNotifyRemindJobPara();
        }
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return UserNotifyRemindJobPara.class;
    }
}
