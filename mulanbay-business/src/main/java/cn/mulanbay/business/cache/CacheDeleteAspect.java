package cn.mulanbay.business.cache;

import cn.mulanbay.business.handler.CacheHandler;
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
public class CacheDeleteAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheDeleteAspect.class);

    @Autowired
    CacheHandler cacheHandler;

    /**
     * 通过@Pointcut指定切入点
     */
    @Pointcut(value = "@annotation(cn.mulanbay.business.cache.MCacheDelete)")
    private void pointcut() {

    }

    /**
     * 在方法执行前后
     *
     * @param point
     * @param cacheDelete
     * @return
     */
    @Around(value = "pointcut() && @annotation(cacheDelete)")
    public Object around(ProceedingJoinPoint point, MCacheDelete cacheDelete) throws Throwable {
        return point.proceed();
    }

    /**
     * 方法执行后
     *
     * @param joinPoint
     * @param cacheDelete
     * @param result
     * @return
     */
    @AfterReturning(value = "pointcut() && @annotation(cacheDelete)", returning = "result")
    public Object afterReturning(JoinPoint joinPoint, MCacheDelete cacheDelete, Object result) {
        String key = KeyParser.parseExpression(cacheDelete.key(), joinPoint);
        cacheHandler.delete(key);
        logger.debug("执行了删除缓存: key=" + key);
        return result;
    }

    /**
     * 方法执行后 并抛出异常
     *
     * @param joinPoint
     * @param cacheDelete
     * @param ex
     */
    @AfterThrowing(value = "pointcut() && @annotation(cacheDelete)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, MCacheDelete cacheDelete, Exception ex) {
        logger.debug("执行了afterThrowing方法");
    }

}
