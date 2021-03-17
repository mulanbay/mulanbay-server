package cn.mulanbay.schedule.impl;

import cn.mulanbay.schedule.NotifiableProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于日志的提醒
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class LogNotifiableProcessor implements NotifiableProcessor {

    private static final Logger logger = LoggerFactory.getLogger(LogNotifiableProcessor.class);

    @Override
    public void notifyMessage(Long taskTriggerId, int code, String title, String message) {
        logger.info("taskTriggerId="+taskTriggerId+",提醒消息,code="+code+",title="+title+",message="+message);
    }
}
