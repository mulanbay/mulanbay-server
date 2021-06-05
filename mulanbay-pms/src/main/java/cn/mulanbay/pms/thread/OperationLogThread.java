package cn.mulanbay.pms.thread;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.util.*;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.LogHandler;
import cn.mulanbay.pms.handler.RewardPointsHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.handler.bean.UserContinueOp;
import cn.mulanbay.pms.persistent.domain.OperationLog;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.domain.SystemLog;
import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 操作记录记录线程
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
public class OperationLogThread extends BaseLogThread {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogThread.class);

    private OperationLog log;

    public OperationLogThread(OperationLog log) {
        super("操作日志");
        this.log = log;
    }

    @Override
    public void run() {
        handleLog(log);
    }

    /**
     * 增加操作日志
     *
     * @param log
     */
    private void handleLog(OperationLog log) {
        try {
            SystemConfigHandler systemConfigHandler = getSystemConfigHandler();
            SystemFunction sf = log.getSystemFunction();
            int errorCode = 0;
            String msgContent = "";
            if (log.getUrlAddress() != null) {
                msgContent = log.getUrlAddress();
            }
            if (sf == null) {
                logger.warn("找不到请求地址[" + log.getUrlAddress() + "],method[" + log.getMethod() + "]功能点配置信息");
            } else {
                errorCode = sf.getErrorCode();
                msgContent += "(" + sf.getName() + ")";
                log.setSystemFunction(sf);
                if (StringUtil.isNotEmpty(sf.getIdField())&&StringUtil.isEmpty(log.getIdValue())) {
                    Map<String, String> paraMap = (Map<String, String>) JsonUtil.jsonToBean(log.getParas(), Map.class);
                    log.setIdValue(this.getParaIdValue(sf, paraMap));
                }
            }
            Date now = new Date();
            log.setStoreTime(now);
            //会比较慢
            log.setHostIpAddress(systemConfigHandler.getHostIpAddress());
            log.setCreatedTime(now);
            //序列化比较耗时间
            //log.setParas(JsonUtil.beanToJson(changeToNormalMap(log.getParaMap())));
            log.setHandleDuration(log.getOccurEndTime().getTime() - log.getOccurStartTime().getTime());
            log.setStoreDuration(log.getStoreTime().getTime() - log.getOccurEndTime().getTime());
            if (log.getUserId() == null) {
                log.setUserId(0L);
                log.setUserName("未知");
            }
            BaseService baseService = BeanFactoryUtil.getBean(BaseService.class);
            baseService.saveObject(log);
            this.handleReward(sf, log);
            this.notifyError(log.getUserId(), errorCode, msgContent);
        } catch (Exception e) {
            logger.error("增加操作日志异常", e);
        }
    }

    /**
     * 积分奖励
     *
     * @param sf
     * @param log
     */
    private void handleReward(SystemFunction sf, OperationLog log) {
        try {
            if (sf != null && sf.getRewardPoint() != 0 && log.getUserId() > 0) {
                //积分奖励(操作类的积分记录管理的messageId为操作记录的编号)
                RewardPointsHandler rewardPointsHandler = BeanFactoryUtil.getBean(RewardPointsHandler.class);
                rewardPointsHandler.rewardPoints(log.getUserId(), sf.getRewardPoint(), sf.getId(),
                        RewardSource.OPERATION, "功能操作奖励", log.getId());
                //连续操作奖励
                CacheHandler cacheHandler = BeanFactoryUtil.getBean(CacheHandler.class);
                String key = CacheKey.getKey(CacheKey.USER_CONTINUE_OP, log.getUserId().toString(), sf.getId().toString());
                String dayString = DateUtil.getFormatDate(log.getOccurStartTime(), "yyyyMMdd");
                int day = Integer.valueOf(dayString);
                UserContinueOp uco = cacheHandler.get(key, UserContinueOp.class);
                //缓存失效时间
                Date now = new Date();
                Date end = DateUtil.getTodayTillMiddleNightDate(now);
                long leftExpired = end.getTime() - now.getTime();
                if (uco == null) {
                    uco = new UserContinueOp();
                    uco.setFistDay(day);
                    uco.setLastDay(day);
                    uco.setDays(1);
                    cacheHandler.set(key, uco, (int) (leftExpired / 1000));
                } else {
                    if (day <= uco.getLastDay()) {
                        //一样，不操作
                        return;
                    } else {
                        uco.setLastDay(day);
                        uco.addDay();
                        cacheHandler.set(key, uco, (int) (leftExpired / 1000));
                        if (uco.getDays() >= 3) {
                            //奖励连续操作(相当于3天以上则双倍奖励，负分的也是一样)
                            int rewards = sf.getRewardPoint() * uco.getDays();
                            rewardPointsHandler.rewardPoints(log.getUserId(), rewards, sf.getId(),
                                    RewardSource.OPERATION, "功能操作连续" + uco.getDays() + "天奖励", log.getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("操作日志积分奖励处理异常", e);
        }

    }

    private void addParaNotFoundSystemLog(OperationLog log) {
        //有可能在request的InputStream里面
        SystemLog systemLog = new SystemLog();
        BeanCopy.copyProperties(log, systemLog);
        systemLog.setLogLevel(LogLevel.WARNING);
        systemLog.setTitle("获取不到请求参数信息");
        systemLog.setContent("获取不到请求参数信息");
        systemLog.setErrorCode(PmsErrorCode.OPERATION_LOG_PARA_IS_NULL);
        BeanFactoryUtil.getBean(LogHandler.class).addSystemLog(systemLog);
    }

    private SystemConfigHandler getSystemConfigHandler() {
        return BeanFactoryUtil.getBean(SystemConfigHandler.class);
    }

}
