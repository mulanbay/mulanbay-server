package cn.mulanbay.pms.web.servlet;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.pms.handler.LogHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.web.servlet.SpringServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统初始化Servlet
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class PmsSpringServlet extends SpringServlet {

    private static final Logger logger = LoggerFactory.getLogger(PmsSpringServlet.class);

    /**
     * 日志记录
     * @param errorCode
     * @param title
     * @param msg
     */
    @Override
    protected void doLog(Integer errorCode, String title, String msg) {
        try {
            if (errorCode == null) {
                errorCode = ErrorCode.SUCCESS;
            }
            LogHandler logHandler = BeanFactoryUtil.getBean(LogHandler.class);
            //增加IP地址和节点
            SystemConfigHandler systemConfigHandler = BeanFactoryUtil.getBean(SystemConfigHandler.class);
            msg = msg + ",IP地址:" + systemConfigHandler.getHostIpAddress() + "，节点编号:" + systemConfigHandler.getNodeId() + ".";
            logHandler.addSystemLog(LogLevel.WARNING, title, msg, errorCode);
        } catch (Exception e) {
            logger.error("doLog 异常", e);
        }

    }
}
