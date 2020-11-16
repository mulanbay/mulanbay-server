package cn.mulanbay.business.handler;

import cn.mulanbay.business.BusinessErrorCode;
import cn.mulanbay.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;

import java.util.List;
import java.util.Locale;

/**
 * 基础Handler，定义通用的方法及流程
 * 项目中涉及到第三方或者在service-controller之间的调用的可以集成及实现
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class BaseHandler {

	@Autowired
	AbstractMessageSource messageSource;

	// 自检失败关闭系统
	boolean scfShutdown = false;

	//处理器名称
	protected String handlerName;

	public BaseHandler() {
	}

	public BaseHandler(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 命令处理(默认不支持)
	 *
	 * @param cmd
	 * @return
	 */
	public HandlerResult handle(String cmd) {
		HandlerResult hr =new HandlerResult();
		List<HandlerCmd> cmdList= this.getSupportCmdList();
		if(StringUtil.isEmpty(cmdList)){
			hr.setCode(BusinessErrorCode.HANDLER_CMD_NOT_SUPPORT);
		}
		return hr;
	}

	/**
	 * 获取支持的命令列表
	 * @return
	 */
	public List<HandlerCmd> getSupportCmdList(){
		return null;
	}

	/**
	 * 初始化，一般为系统启动时调用
	 */
	public void init() {

	}

	/**
	 * 重新加载，在系统运行时操作，需要线程同步
	 */
	public void reload() {

	}

	/**
	 * 容器destroy时调用
	 */
	public void destroy() {

	}

	/**
	 * 自检，一般为系统启动时调用
	 *
	 * @return
	 */
	public Boolean selfCheck() {
		return true;
	}

	public boolean isScfShutdown() {
		return scfShutdown;
	}

	public void setScfShutdown(boolean scfShutdown) {
		this.scfShutdown = scfShutdown;
	}

	/**
	 * Handler名称
	 *
	 * @return
	 */
	public String getHandlerName() {
		return handlerName;
	}

	/**
	 * 获取message文件的常量定义，目前没什么作用
	 * @param key
	 * @return
	 */
	String getConfig(String key) {
		try {
			return messageSource.getMessage(key, null, Locale.ROOT);
		} catch (NoSuchMessageException e) {
			return null;
		}
	}

	/**
	 * 读取配置信息
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	String getConfig(String key, String defaultValue) {
		String s = getConfig(key);
		if (s == null || s.isEmpty()) {
			return defaultValue;
		} else {
			return s;
		}
	}

	int getIntegerConfig(String key, int defaultValue) {
		String s = getConfig(key);
		if (s == null || s.isEmpty()) {
			return defaultValue;
		} else {
			return Integer.valueOf(s);
		}
	}


	/**
	 * 处理信息
	 * @return
	 */
	public HandlerInfo getHandlerInfo(){
		return new HandlerInfo(this.handlerName);
	}

	public boolean isDoSystemLog(){
		return false;
	}
}
