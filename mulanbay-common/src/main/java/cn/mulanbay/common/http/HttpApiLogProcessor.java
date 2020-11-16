package cn.mulanbay.common.http;

import java.util.Date;

/**
 * ${DESCRIPTION}
 * 第三方API日志处理
 * @author fenghong
 * @create 2017-10-12 22:11
 **/
public interface HttpApiLogProcessor {

    /**
     * 记录http请求日志
     * @param url
     * @param method
     * @param request
     * @param response
     * @param startTime
     * @param endTime
     */
    public void doHttpApiLog(String url, String method, Object request, HttpResult response, Date startTime, Date endTime);
}
