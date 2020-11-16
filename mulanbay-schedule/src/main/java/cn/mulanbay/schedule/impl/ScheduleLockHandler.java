package cn.mulanbay.schedule.impl;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.lock.RedisDistributedLock;
import cn.mulanbay.schedule.lock.LockStatus;
import cn.mulanbay.schedule.lock.ScheduleLocker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * ${DESCRIPTION}
 * 加强版的分布式锁，解决上锁和设置超时时间无法同时操作
 * 该类需要实例化一个RedisDistributedLock（目前在xml中配置一个bean）
 * @author fenghong
 * @create 2018-01-03 21:45
 **/
public class ScheduleLockHandler extends BaseHandler implements ScheduleLocker {

    private static Logger logger = Logger.getLogger(ScheduleLockHandler.class);

    @Value("${system.namespace}")
    String namespace;

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    public ScheduleLockHandler() {
        super("API调度锁");
    }

    /**
     * 获取锁，不采用重试
     * 对于锁的获取与释放，RedisDistributedLock已经默认使用了uuid来实现
       如果想需要自己实现，则需要在接口中传入参数（其实也是使用uuid）
     * @param identityKey
     * @param expiredSeconds
     * @return
     */
    @Override
    public LockStatus lock(String identityKey, long expiredSeconds) {
        String fullKey = getFullKey(identityKey);
        try {
            boolean b = redisDistributedLock.lock(fullKey,expiredSeconds*1000,0);
            logger.debug(this.getHandlerName()+"获取锁["+fullKey+"]结果:"+b);
            if(b){
                return LockStatus.SUCCESS;
            }else {
                return LockStatus.EXITED;
            }
        } catch (Exception e) {
            logger.error(this.getHandlerName()+"获取锁["+fullKey+"]异常:",e);
            return LockStatus.ERROR;
        }
    }

    @Override
    public LockStatus unlock(String identityKey) {
        String fullKey = getFullKey(identityKey);
        try {
            boolean b = redisDistributedLock.releaseLock(fullKey);
            logger.debug(this.getHandlerName()+"释放锁["+fullKey+"]结果:"+b);
            if(b){
                return LockStatus.SUCCESS;
            }else {
                return LockStatus.ERROR;
            }
        } catch (Exception e) {
            logger.error(this.getHandlerName()+"释放锁["+fullKey+"]异常:",e);
            return LockStatus.ERROR;        }
    }

    private String getFullKey(String key){
        return namespace+":"+key;
    }

}
