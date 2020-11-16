package cn.mulanbay.business.handler;

import cn.mulanbay.business.BusinessErrorCode;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler管理类
 * 系统初始化时由它来调用各个Handler的初始化工作
 * 系统关闭时由它来调用各个Handler的资源回收工作
 * //@see cn.mulanbay.web.servlet.SpringServlet
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class HandlerManager {

	@Autowired
	private List<BaseHandler> handlerList;

	public List<BaseHandler> getHandlerList() {
		return handlerList;
	}

	public void setHandlerList(List<BaseHandler> handlerList) {
		this.handlerList = handlerList;
	}

	/**
	 * 处理命令
	 * @param clz
	 * @param cmd
	 * @param isSync
	 * @return
	 */
	public HandlerResult handleCmd(Class clz,String cmd,boolean isSync){
		BaseHandler handler = this.getHandler(clz);
		if(handler==null){
			HandlerResult hr =new HandlerResult();
			hr.setCode(BusinessErrorCode.HANDLER_NOT_FOUND);
			return hr;
		}else{
			return handler.handle(cmd);
		}
	}

	/**
	 * 获取handler
	 * @param className
	 * @return
	 */
	public BaseHandler getHandler(String className){
		try {
			Class clz = Class.forName(className);
			return this.getHandler(clz);
		} catch (Exception e) {
			throw new ApplicationException(BusinessErrorCode.CLASS_NAME_NOT_FOUND,"未找到相关类");
		}
	}

	/**
	 * 获取handler
	 * @param clz
	 * @return
	 */
	public BaseHandler getHandler(Class clz){
		if(StringUtil.isEmpty(handlerList)){
			return null;
		}else{
			for(BaseHandler bh : handlerList){
				if(bh.getClass().equals(clz)){
					return bh;
				}
			}
		}
		return null;
	}

	/**
	 * 获取Handler信息列表
	 * @return
	 */
	public List<HandlerInfo> getHandlerInfoList(){
		if(StringUtil.isEmpty(handlerList)){
			return new ArrayList<>();
		}else{
			List<HandlerInfo> res = new ArrayList<>();
			for(BaseHandler bh : handlerList){
				res.add(bh.getHandlerInfo());
			}
			return res;
		}
	}


}
