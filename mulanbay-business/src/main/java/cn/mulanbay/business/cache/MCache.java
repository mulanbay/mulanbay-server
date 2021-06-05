package cn.mulanbay.business.cache;

import java.lang.annotation.*;

/**
 * 缓存注解
 * @author fenghng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited()
public @interface MCache {

    /**
     * 失效时间（秒）
     * @return
     */
    int expires() default 300;

    /**
     * 缓存可以
     * @return
     */
    String key() default "yc";

    /**
     * 进行缓存处理时是否需要加锁
     * 防止缓存击穿
     * @return
     */
    boolean lock() default false;

    /**
     * 是否支持返回空对象
     * 防止缓存穿透，可以设置NullObject
     * @return
     */
    boolean supportNull() default false;

    /**
     * 防止缓存雪崩
     * @return
     */
    boolean expireRandom() default false;
}
