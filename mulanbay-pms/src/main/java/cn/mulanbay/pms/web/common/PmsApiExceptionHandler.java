package cn.mulanbay.pms.web.common;

import cn.mulanbay.common.util.IPAddressUtil;
import cn.mulanbay.pms.handler.LogHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.handler.TokenHandler;
import cn.mulanbay.pms.persistent.domain.SystemFunction;
import cn.mulanbay.pms.persistent.domain.SystemLog;
import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.web.bean.LoginUser;
import cn.mulanbay.web.common.ApiExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理类
 *
 * @author fenghong
 */
@ControllerAdvice
public class PmsApiExceptionHandler extends ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(PmsApiExceptionHandler.class);

    @Value("${system.need.systemLog}")
    boolean needSystemLog;

    @Autowired
    LogHandler logHandler;

    @Autowired
    TokenHandler tokenHandler;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Override
    protected boolean doSystemLog() {
        return needSystemLog;
    }

    @Override
    protected void addSystemLog(HttpServletRequest request, Class exceptionClass, String title, String msg, int errorCode) {
        try {
            SystemLog systemLog = new SystemLog();
            systemLog.setLogLevel(LogLevel.ERROR);
            systemLog.setTitle(title);
            systemLog.setContent(msg);
            systemLog.setErrorCode(errorCode);
            long userId = 0;
            String userName = "系统操作";
            if (request != null) {
                LoginUser loginUser = tokenHandler.getLoginUser(request);
                if (loginUser != null) {
                    userId = loginUser.getUserId();
                    userName = loginUser.getUsername();
                }
                String url = request.getServletPath();
                String method = request.getMethod();
                SystemFunction sf = systemConfigHandler.getFunction(url, method);
                systemLog.setSystemFunction(sf);
                systemLog.setUrlAddress(url);
                systemLog.setMethod(method);
                systemLog.setIpAddress(IPAddressUtil.getIpAddress(request));
                systemLog.setExceptionClassName(exceptionClass.getName());
                //多线程环境下会导致有时获取不到?,需要克隆
                systemLog.setParaMap(request.getParameterMap());
            }
            systemLog.setUserId(userId);
            systemLog.setUserName(userName);
            logHandler.addSystemLog(systemLog);
        } catch (Exception e) {
            logger.error("添加系统日志异常", e);
        }
    }

}
