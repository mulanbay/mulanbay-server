package cn.mulanbay.business.cache;

import java.lang.annotation.*;

/**
 * 缓存注解（删除）
 * @author fenghng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited()
public @interface MCacheDelete {

    /**
     * 缓存可以
     * @return
     */
    String key() default "yc";

}
