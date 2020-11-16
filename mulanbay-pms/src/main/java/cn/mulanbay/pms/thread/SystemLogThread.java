package cn.mulanbay.pms.thread;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.ErrorCodeDefine;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.domain.SystemLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 系统日记记录线程
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
public class SystemLogThread extends BaseLogThread {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogThread.class);

    private SystemLog log;

    public SystemLogThread(SystemLog log) {
        super("系统日志");
        this.log = log;
    }

    @Override
    public void run() {
        handleLog(log);
    }

    /**
     * 增加系统日志
     *
     * @param log
     */
    private void handleLog(SystemLog log) {
        try {
            SystemConfigHandler systemConfigHandler = getSystemConfigHandler();
            ErrorCodeDefine ec = systemConfigHandler.getErrorCodeDefine(log.getErrorCode());
            if (ec != null && ec.getLoggable() == true) {
                SystemFunction sf = log.getSystemFunction();
                if (sf != null) {
                    log.setSystemFunction(sf);
                    log.setIdValue(this.getParaIdValue(sf, log.getParaMap()));
                }
                Date now = new Date();
                log.setStoreTime(now);
                //会比较慢
                log.setHostIpAddress(systemConfigHandler.getHostIpAddress());
                log.setCreatedTime(now);
                Map map = log.getParaMap();
                if (map != null && !map.isEmpty()) {
                    //序列化比较耗时间
                    log.setParas(JsonUtil.beanToJson(changeToNormalMap(map)));
                }
                log.setStoreDuration(log.getStoreTime().getTime() - log.getOccurTime().getTime());
                String content = log.getContent();
                if (content.length() >= 2000) {
                    content = content.substring(0, 2000);
                    log.setContent(content);
                }
                BaseService baseService = BeanFactoryUtil.getBean(BaseService.class);
                baseService.saveObject(log);
            }
            this.notifyError(log.getUserId(), ec, log.getContent());
        } catch (Exception e) {
            String msg = "增加系统日志异常，log=" + log.getContent();
            logger.error(msg, e);
        }
    }

    private SystemConfigHandler getSystemConfigHandler() {
        return BeanFactoryUtil.getBean(SystemConfigHandler.class);
    }

}
