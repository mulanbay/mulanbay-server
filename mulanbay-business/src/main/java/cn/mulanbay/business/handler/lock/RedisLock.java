package cn.mulanbay.business.handler.lock;

import java.lang.annotation.*;

/**
 * ${DESCRIPTION}
 * 分布式锁注解
 * 参考：{@link https://my.oschina.net/dengfuwei/blog/1600681}
 * @author fenghong
 * @create 2018-01-03 9:24
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {

    /** 缓存区域（有keepMills参数后，这个参数没什么作用）*/
    String value() default "";

    /** 锁的资源，redis的key*/
    String key()  default "";

    /** 持锁时间,单位毫秒*/
    long keepMills() default 30000;

    /** 当获取失败时候动作*/
    LockFailAction action() default LockFailAction.CONTINUE;

    /**key的类型，默认为SPEL，因为锁的基本上是资源，极少情况下去锁某个接口或者方法*/
    KeyType keyType() default KeyType.SPEL;

    /** 重试的间隔时间,设置GIVEUP忽略此项*/
    long sleepMills() default 200;

    /** 重试次数*/
    int retryTimes() default 5;

    /**
     * 锁失败操作
     */
    public enum LockFailAction{
        /** 放弃 */
        GIVEUP,
        /** 继续 */
        CONTINUE;
    }

    /**
     * 分布式锁key的类型
     */
    public enum KeyType{
        /** 定义，即key的值（针对接口或者方法级别） */
        DEFINE,
        /** spring表达式语句 （针对资源级别）*/
        SPEL,
        /** 采用参数拼装模式（复杂类型，通常不会采用）*/
        PARAS;
    }

}
