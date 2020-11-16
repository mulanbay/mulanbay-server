package cn.mulanbay.schedule.para;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 调度job的参数定义标签
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JobParameter {

    /**
     * 名称
     * @return
     */
    String name();

    /**
     * 数据类型
     * @return
     */
    Class dataType();

    /**
     * 精度或者长度
     * @return
     */
    int scale() default 0;

    /**
     * 是否必填
     */
    boolean notNull() default true;

    /**
     * 描述
     * @return
     */
    String desc() default "";

    /**
     * 编辑类型
     * @return
     */
    EditType editType() default  EditType.TEXT;

    /**
     * 供页面编辑的数据
     * @return
     */
    String editData() default "";
}
