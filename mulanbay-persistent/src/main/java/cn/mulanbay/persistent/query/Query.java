package cn.mulanbay.persistent.query;

import cn.mulanbay.persistent.query.Parameter.Operator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 查询标签，定义规则
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface Query {

	String fieldName();
	
	Operator op();

	/**
	 * 	可以人工指向(refer有两种情况：
	 * 	一种是所需要查询的列名由另外一个字段定义
	 * 	比如：查询的日期区段为2017-01-01--2017-02-01，但是所被查询的字段可能是保存时间、操作时间
	 * 	另一种是比较类型由另外一个字段定义
	 * 	比如：字段时长（duration） 与10 的比较，可以是大于，也可以是小与，也可以是等于
	 */
	String referFieldName() default "";

	ReferType referType() default ReferType.FIELD_REFER;

	//默认为空，说明是普通类型
	CrossType crossType() default CrossType.NULL;

	//TODO 一些查询中需要支持为空的条件
	boolean supportNullValue() default false;

}
