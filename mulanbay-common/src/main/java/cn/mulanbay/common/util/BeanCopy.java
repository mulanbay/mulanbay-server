package cn.mulanbay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean对象复制
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BeanCopy extends BeanUtils {

	private static final Logger logger = LoggerFactory.getLogger(BeanCopy.class);

	/**
	 * 解决数字类型封装类型为空时向基本变量类型复制
	 * source的空字段默认复制
	 *
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void copyProperties(Object source, Object target)
			throws BeansException {
		copyProperties(source,target,true);
	}

	/**
	 * 解决数字类型封装类型为空时向基本变量类型复制
	 *
	 * @param source
	 * @param target
	 * @param copyNull source的空字段需不需要复制
	 * @throws BeansException
	 */
	public static void copyProperties(Object source, Object target,boolean copyNull)
			throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (copyNull||value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						logger.error("Bean copy copyProperties error",ex);
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}

	/**
	 * 实现beanToMap转换
	 *
	 * @param obj
	 * @return
	 */
	public static Map<String, String> transBean2StringMap(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!"class".equals(key)) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (value != null) {
						map.put(key, value.toString());
					}

				}

			}
		} catch (Exception e) {
			logger.error("Bean copy copyProperties error",e);
			throw new RuntimeException("复制" + obj + "到bean异常");
		}

		return map;

	}

	/**
	 * 实现beanToMap转换
	 *
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!"class".equals(key)) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}

			}
		} catch (Exception e) {
			logger.error("transBean2Map error",e);
			throw new RuntimeException("复制" + obj + "到bean异常");
		}

		return map;

	}

}
