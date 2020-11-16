package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.handler.PmsMessageSendHandler;
import cn.mulanbay.pms.persistent.domain.UserMessage;
import cn.mulanbay.pms.persistent.service.UserRemindMessageService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 发送提醒消息（基于数据库的轮询模式）
 * 待发送的消息已经被保存在消息表里面，该job只是定时去取消息发送
 * 根据expectSendTime字段判断是否要发送
 * todo 后期可以选择发送微信消息等
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class SendDatabaseUserMessageJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(SendDatabaseUserMessageJob.class);

    UserRemindMessageService userRemindMessageService;

    BaseService baseService;

    PmsMessageSendHandler pmsMessageSendHandler;

    SendDatabaseUserMessageJobPara para;

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        userRemindMessageService = BeanFactoryUtil.getBean(UserRemindMessageService.class);
        baseService = BeanFactoryUtil.getBean(BaseService.class);
        pmsMessageSendHandler = BeanFactoryUtil.getBean(PmsMessageSendHandler.class);
        Date compareDate = new Date(System.currentTimeMillis() + para.getCompareMinutes() * 60 * 1000);
        List<UserMessage> list = userRemindMessageService.getNeedSendMessage(1, para.getMaxRows(), 3, compareDate);
        if (list.isEmpty()) {
            result.setComment("没有待发送的消息");
        } else {
            for (UserMessage message : list) {
                boolean isShutdown = this.isShutDown();
                if (isShutdown) {
                    logger.warn("调度已经关闭，停止发送消息");
                    break;
                }
                pmsMessageSendHandler.sendMessage(message);
            }
            result.setComment("本次发送" + list.size() + "个用户消息");
            result.setExecuteResult(JobExecuteResult.SUCCESS);
        }
        return result;
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        para = this.getTriggerParaBean();
        if (para == null) {
            para = new SendDatabaseUserMessageJobPara();
        }
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return SendDatabaseUserMessageJobPara.class;
    }
}
