package cn.mulanbay.business.handler.lock;

import cn.mulanbay.business.BusinessErrorCode;
import cn.mulanbay.common.exception.ApplicationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ${DESCRIPTION}
 * 基于AOP的分布式锁实现
 * @author fenghong
 * @create 2018-01-03 11:10
 **/
@Component
@Aspect
public class DistributedLockAspectConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLockAspectConfiguration.class);

    @Autowired
    protected DistributedLock distributedLock;

    @Pointcut("@annotation(cn.mulanbay.business.handler.lock.RedisLock)")
    private void lockPoint(){

    }

    @Around("lockPoint()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        logger.debug("开始执行分布式锁...");
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String key = getKey(redisLock,method,pjp);
        int retryTimes = redisLock.action().equals(RedisLock.LockFailAction.CONTINUE) ? redisLock.retryTimes() : 0;
        boolean lock = distributedLock.lock(key, redisLock.keepMills(), retryTimes, redisLock.sleepMills());
        if(!lock) {
            logger.error("get lock failed : " + key);
            throw new ApplicationException(BusinessErrorCode.BUSINESS_LOCK_ERROR);
        }
        //得到锁,执行方法，释放锁
        logger.debug("get lock success : " + key);
        try {
            return pjp.proceed();
        } catch (Exception e) {
            logger.error("execute locked method occured an exception", e);
            throw new ApplicationException(BusinessErrorCode.BUSINESS_LOCK_EXECUTE_PROCEED_ERROR);
        } finally {
            boolean releaseResult = distributedLock.releaseLock(key);
            logger.debug("release lock : " + key + (releaseResult ? " success" : " failed"));
        }
    }

    /**
     * 获取缓存的键值
     * @param redisLock
     * @param method
     * @param pjp
     * @return
     */
    private String getKey( RedisLock redisLock,Method method,ProceedingJoinPoint pjp){
        RedisLock.KeyType keyType = redisLock.keyType();
        String key = redisLock.key();
        if(keyType==RedisLock.KeyType.SPEL){
            Object[] args = pjp.getArgs();
            key = parseSpelKey(key,method,args);
        }else if(keyType==RedisLock.KeyType.PARAS){
            Object[] args = pjp.getArgs();
            key = Arrays.toString(args);
        }
        return key;
    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     * @param key
     * @param method
     * @param args
     * @return
     */
    private String parseSpelKey(String key,Method method,Object [] args){
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String [] paraNameArr=u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for(int i=0;i<paraNameArr.length;i++){
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context,String.class);
    }

}
