package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.pms.persistent.domain.OperationLog;
import cn.mulanbay.pms.persistent.domain.SystemLog;
import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.thread.OperationLogThread;
import cn.mulanbay.pms.thread.SystemLogThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 日志处理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class LogHandler extends BaseHandler {

    @Autowired
    ThreadPoolHandler threadPoolHandler;

    public LogHandler() {
        super("日志处理");
    }

    @Override
    public void init() {

    }

    public void addOperationLog(OperationLog log) {
        OperationLogThread thread = new OperationLogThread(log);
        threadPoolHandler.pushThread(thread);
    }

    public void addSystemLog(SystemLog log) {
        if (log.getOccurTime() == null) {
            log.setOccurTime(new Date());
        }
        SystemLogThread thread = new SystemLogThread(log);
        threadPoolHandler.pushThread(thread);
    }

    public void addSystemLog(LogLevel logLevel, String title, String content, int errorCode) {
        SystemLog log = new SystemLog();
        log.setUserId(0L);
        log.setUserName("系统操作");
        log.setTitle(title);
        log.setContent(content);
        log.setLogLevel(logLevel);
        log.setErrorCode(errorCode);
        if (log.getOccurTime() == null) {
            log.setOccurTime(new Date());
        }
        SystemLogThread thread = new SystemLogThread(log);
        threadPoolHandler.pushThread(thread);
    }

}
