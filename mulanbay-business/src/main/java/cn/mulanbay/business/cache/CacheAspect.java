package cn.mulanbay.business.cache;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.business.handler.lock.DistributedLock;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.NumberUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 缓存
 * @Author: fenghong
 * @Create : 2021/6/4
 */
@Aspect
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Autowired
    CacheHandler cacheHandler;

    @Autowired(required = false)
    DistributedLock distributedLock;

    /**
     * 空对象,预防缓存穿透时使用
     */
    private static NullObject nullObject = new NullObject();

    /**
     * 通过@Pointcut指定切入点
     */
    @Pointcut(value = "@annotation(cn.mulanbay.business.cache.MCache)")
    private void pointcut() {

    }

    /**
     * 在方法执行前后
     *
     * @param point
     * @param cache
     * @return
     */
    @Around(value = "pointcut() && @annotation(cache)")
    public Object around(ProceedingJoinPoint point, MCache cache) {
        boolean lock = false;
        String lockKey = null;
        try {
            logger.debug("开始进行YcCache缓存操作");
            String key = KeyParser.parseExpression(cache.key(), point);
            Object object = cacheHandler.get(key);
            if (object != null) {
                if (object instanceof NullObject) {
                    logger.debug("获取到缓存的空对象");
                    return null;
                } else {
                    return object;
                }
            }
            logger.debug("缓存未找到，执行具体逻辑业务");
            if (cache.lock()) {
                lockKey = "lock:" + key;
                lock = distributedLock.lock(lockKey, 3000L, 3);
                if (lock == true) {
                    //此时应该需要重新再去查一下缓存，有可能其他线程获取设置了
                    object = cacheHandler.get(key);
                    if (object != null) {
                        //return object;
                        return (object instanceof NullObject) ? null : object;
                    }
                    object = point.proceed();
                }
            } else {
                object = point.proceed();
            }
            if (object != null) {
                int exp = cache.expires();
                if (cache.expireRandom()) {
                    exp += NumberUtil.getRandom(10);
                }
                cacheHandler.set(key, object, exp);
            } else if (cache.supportNull()) {
                // 进行缓存穿透逻辑
                logger.debug("设置空对象");
                cacheHandler.set(key, nullObject, cache.expires());
            }
            return object;
        } catch (Throwable throwable) {
            logger.error("缓存操作异常", throwable);
            throw new ApplicationException(ErrorCode.GET_CACHE_ERROR);
        } finally {
            if (lock) {
                distributedLock.releaseLock(lockKey);
            }
        }
    }

    /**
     * 方法执行后
     *
     * @param joinPoint
     * @param cache
     * @param result
     * @return
     */
    @AfterReturning(value = "pointcut() && @annotation(cache)", returning = "result")
    public Object afterReturning(JoinPoint joinPoint, MCache cache, Object result) {
        logger.debug("执行了afterReturning方法: result=" + result);
        return result;
    }

    /**
     * 方法执行后 并抛出异常
     *
     * @param joinPoint
     * @param cache
     * @param ex
     */
    @AfterThrowing(value = "pointcut() && @annotation(cache)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, MCache cache, Exception ex) {
        logger.debug("执行了afterThrowing方法");
    }

}
