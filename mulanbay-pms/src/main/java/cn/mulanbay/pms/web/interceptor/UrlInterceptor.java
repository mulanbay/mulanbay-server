package cn.mulanbay.pms.web.interceptor;

import cn.mulanbay.common.util.IPAddressUtil;
import cn.mulanbay.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * URL拦截
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class UrlInterceptor extends BaseInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(UrlInterceptor.class);

    private String interceptorName = "URL拦截器";

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception exp)
            throws Exception {
        String reqUrl = request.getServletPath();
        logger.debug("[" + interceptorName + "]拦截到" + reqUrl
                + "后afterCompletion。");
        if (exp != null) {
            logger.error("[" + interceptorName + "]拦截到异常", exp);
        }
        super.afterCompletion(request, response, handler, exp);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView mav)
            throws Exception {
        String reqUrl = request.getServletPath();
        logger.debug("[" + interceptorName + "]拦截到" + reqUrl + "后postHandle。");
        super.postHandle(request, response, handler, mav);
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String reqUrl = request.getServletPath();
        String ip = IPAddressUtil.getIpAddress(request);
        String sessionId = request.getSession().getId();
        logger.info("[" + interceptorName + "]收到来自[" + ip + "]请求：" + reqUrl
                + ",sessionId=" + sessionId);
        if (logger.isDebugEnabled()) {
            logger.debug("请求参数：" + JsonUtil.beanToJson(request.getParameterMap()));
        }
        return true;
    }

}
