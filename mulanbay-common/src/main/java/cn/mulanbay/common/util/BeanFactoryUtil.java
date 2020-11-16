package cn.mulanbay.common.util;

import cn.mulanbay.common.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring的ApplicationContext管理
 * 由servlet初始化时设置
 * 这样各个非spring管理的对象也可以使用spring里面的bean
 *
 * //@see cn.mulanbay.web.servlet.SpringServlet
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BeanFactoryUtil {

	private static final Logger logger = LoggerFactory.getLogger(BeanFactoryUtil.class);

	private static ApplicationContext s_applicationContext;

	/**
	 * 初始bean
	 * 
	 * @param strs
	 *            configLocations eg：spring-config.xml(默认)
	 */
	public static void initBean(String... strs) {
		try {
			String[] contextStrings;
			if (strs == null || strs.length == 0) {
				contextStrings = new String[] { "spring-config.xml" };
			} else {
				contextStrings = strs;
			}
			s_applicationContext = new ClassPathXmlApplicationContext(
					contextStrings);
		} catch (Exception e) {
			logger.error("initBean error",e);
			System.exit(-1);
		}
	}

	protected static Object getBean(String beanName) {
		try {
			return s_applicationContext.getBean(beanName);
		} catch (RuntimeException ex) {
			throw new ApplicationException("bean:" + beanName
					+ " 在配置文件spring-config中不存在\n", ex);
		}
	}

	public static <T> T getBean(Class<T> cls) {
		try {
			return s_applicationContext.getBean(cls);
		} catch (RuntimeException ex) {
			throw new ApplicationException("bean:" + cls
					+ " 在配置文件spring-config中不存在\n", ex);
		}
	}

	/**
	 * 设置ApplicationContext
	 * 
	 * @param context
	 *            the s_applicationContext to set
	 */
	public static void setApplicationContext(ApplicationContext context) {
		s_applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return s_applicationContext;
	}

}
