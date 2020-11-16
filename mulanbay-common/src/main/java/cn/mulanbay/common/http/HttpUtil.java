package cn.mulanbay.common.http;

import cn.mulanbay.common.exception.ErrorCode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * http请求操作
 *
 * @author fenghong
 * @create 2017-10-11 22:17
 **/
public class HttpUtil {

    private static HttpApiLogProcessor logProcessor;

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private final static String ENCODE="utf-8";

    /**
     *
     * @param client 一些请求可能需要自定义客户端，比如需要证书的
     * @param url
     * @param requestData
     * @param contentType
     * @return
     */
    public static HttpResult doPostRequest(CloseableHttpClient client,String url,String requestData,String contentType){
        HttpResult result = new HttpResult();
        boolean selfClient=false;
        Date startTime = new Date();
        try {
            HttpPost httpPost = new HttpPost(url);//创建HttpPost对象
            StringEntity stringEntity = new StringEntity(requestData,ENCODE);
            if(contentType!=null){
                stringEntity.setContentType(HttpContentType.JSON);
            }
            httpPost.setEntity(stringEntity);//设置请求数据
            if(client==null){
                client = getClient();
            }else {
                // 采用自定义client
                selfClient=true;
            }
            CloseableHttpResponse response = client.execute(httpPost);
            int code =response.getStatusLine().getStatusCode();
            String content= EntityUtils.toString(response.getEntity(),ENCODE);
            result.setStatusCode(code);
            result.setResponseData(content);
        } catch (Exception e) {
            logger.error("doPost error:",e);
            result.setStatusCode(ErrorCode.HTTP_ERROR);
            result.setErrorInfo(e.getMessage());
            result.setExceptionClass(e.getClass());
        } finally {
            closeHttpClient(client,selfClient);
            Date endTime = new Date();
            doLog(url,"POST",requestData,result,startTime,endTime);
        }
        return result;
    }

    /**
     * POST请求
     * @param url
     * @param requestData
     * @param contentType
     * @return
     */
    public static HttpResult doPost(String url,String requestData,String contentType){
        return doPostRequest(null,url,requestData,contentType);
    }

    /**
     * 以json模式的POST请求
     * @param url
     * @param jsonStr
     * @return
     */
    public static HttpResult doPostJson(String url,String jsonStr){
        return doPost(url,jsonStr,HttpContentType.JSON);
    }

    /**
     * GET请求
     * @param url
     * @return
     */
    public static HttpResult doGetRequest(CloseableHttpClient client,String url){
        HttpResult result = new HttpResult();
        boolean selfClient=false;
        Date startTime = new Date();
        try {
            HttpGet httpGet = new HttpGet(url);
            if(client==null){
                client = getClient();
            }else {
                // 采用自定义client
                selfClient=true;
            }            CloseableHttpResponse response = client.execute(httpGet);
            int code =response.getStatusLine().getStatusCode();
            String content= EntityUtils.toString(response.getEntity(),ENCODE);
            result.setStatusCode(code);
            result.setResponseData(content);
        } catch (Exception e) {
            logger.error("doGet error:",e);
            result.setStatusCode(ErrorCode.HTTP_ERROR);
            result.setErrorInfo(e.getMessage());
            result.setExceptionClass(e.getClass());
        } finally {
            closeHttpClient(client,selfClient);
            Date endTime = new Date();
            doLog(url,"GET",null,result,startTime,endTime);
        }
        return result;
    }

    private static void closeHttpClient(CloseableHttpClient client,boolean selfClient){
        // 统一的链接池模式client不需要关闭
        if(selfClient){
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e) {
                    logger.error("client.close error:",e);
                }
            }
        }
    }
    /**
     * GET请求
     * @param url
     * @return
     */
    public static HttpResult doHttpGet(String url){
        return doGetRequest(null,url);
    }

    private static CloseableHttpClient getClient(){
        return httpClientGenerator.getClient();
    }

    private static void doLog(String url,String method,Object request,HttpResult response,Date startTime,Date endTime){
        if(logProcessor!=null){
            logProcessor.doHttpApiLog(url,method,request,response,startTime,endTime);
        }
    }

    public static void setLogProcessor(HttpApiLogProcessor logProcessor) {
        HttpUtil.logProcessor = logProcessor;
    }
}
