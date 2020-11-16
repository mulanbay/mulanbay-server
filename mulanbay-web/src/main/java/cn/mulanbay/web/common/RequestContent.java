package cn.mulanbay.web.common;


import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.MapUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 请求内容处理
 * 对于POST请求及contentType为application/json的解析请求体内容为通用的ParameterMap
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class RequestContent {

    private HttpServletRequest request;

    private String contentType;

    public RequestContent(HttpServletRequest request) {
        this.request = request;
        this.contentType = request.getContentType();
    }

    /**
     * 获取请求参数
     * @return
     */
    public Map getParameterMap(){
        String reqMethod = request.getMethod();
        if("GET".equals(reqMethod)){
            return MapUtil.changeRequestMapToNormalMap(request.getParameterMap());
        }else{
            if(contentType!=null&&contentType.startsWith("application/json")){
                String postBodyStr = HttpHelper.getRequestPayload(request);
                return (Map) JsonUtil.jsonToBean(postBodyStr,Map.class);
            }else{
                return MapUtil.changeRequestMapToNormalMap(request.getParameterMap());
            }
        }
    }

    /**
     * 获取请求参数
     * @return
     */
    public String getParameterMapString(){
        String reqMethod = request.getMethod();
        if("POST".equals(reqMethod)&&(contentType!=null&&contentType.startsWith("application/json"))){
            String postBodyStr =HttpHelper.getBodyString(request);
            return postBodyStr;
        }else {
            return JsonUtil.beanToJson(this.getParameterMap());
        }
    }

    /**
     * 获取签名数据
     * @return
     */
    public String getSign(){
        return request.getHeader("sign");
    }

    public String getUrl(){
        return request.getRequestURI();
    }

    public String getMethod(){
        return request.getMethod();
    }
}
