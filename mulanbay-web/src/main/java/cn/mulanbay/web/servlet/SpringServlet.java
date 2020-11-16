package cn.mulanbay.web.servlet;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.HandlerManager;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.thread.ThreadManager;
import cn.mulanbay.common.util.BeanFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;
import java.io.FileNotFoundException;

/**
 * 系统的一些初始化操作，并获取web容器中的Bean管理器，方便其他模块使用
 * 适用于war启动类型，spring boot类型换成StartListener模式
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class SpringServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(SpringServlet.class);

	/**
	 * WebApplicationContext
	 */
	private static WebApplicationContext s_wac;

	/**
	 * destroy
	 */
	@Override
	public void destroy() {
		logger.info("SpringContext begin to destroy....................");
		HandlerManager hm = BeanFactoryUtil.getBean(HandlerManager.class);
		for (BaseHandler bh : hm.getHandlerList()) {
			logger.info(bh.getHandlerName()+" Handler begin to destroy...");
			bh.destroy();
			if(bh.isDoSystemLog()){
				doLog(null,bh.getHandlerName()+"关闭",bh.getHandlerName()+"关闭成功");
			}
			logger.error(bh.getHandlerName() + " Handler destroyed。");
		}
		logger.info("关闭了"+hm.getHandlerList().size()+"个Handler");
		ThreadManager.getInstance().stopAll();
		logger.info("SpringContext Destroyed....................");
	}

	/**
	 * init
	 * 
	 * @throws FileNotFoundException
	 */
	@Override
	public void init() {
		try {
			logger.info("SpringContext init....................");
			s_wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
			BeanFactoryUtil.setApplicationContext(s_wac);
			initSystem();
			logger.info("SpringContext end.....................");
		} catch (Exception e) {
			logger.error("SpringServlet初始化异常", e);
			System.exit(-1);
		}
	}

	/**
	 * 获取WebApplicationContext
	 * 
	 * @return WebApplicationContext WebApplicationContext
	 */
	public static WebApplicationContext getBeanFactory() {
		return s_wac;
	}

	/**
	 * 初始化信息
	 */
	private void initSystem() {
		HandlerManager hm = BeanFactoryUtil.getBean(HandlerManager.class);
		for (BaseHandler bh : hm.getHandlerList()) {
			logger.info(bh.getHandlerName()+" Handler begin to init...");
			bh.init();
			if (!bh.selfCheck()) {
				logger.error(bh.getHandlerName() + "自检失败。");
				if (bh.isScfShutdown()) {
					logger.error("因" + bh.getHandlerName() + "自检失败，关闭系统。");
					System.exit(-1);
				}
			}
			if(bh.isDoSystemLog()){
				doLog(null,bh.getHandlerName()+"初始化",bh.getHandlerName()+"初始化成功");
			}
			logger.info(bh.getHandlerName()+" Handler init end");
		}
		logger.info("初始化了"+hm.getHandlerList().size()+"个Handler");
		doLog(ErrorCode.SYSTEM_STARTED,"系统启动","系统启动成功");

	}

	/**
	 * 子类实现
	 * @param title
	 * @param msg
	 */
	protected void doLog(Integer errorCode,String title,String msg){

	}

}
