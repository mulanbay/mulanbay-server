package cn.mulanbay.common.http;

import org.apache.http.HttpStatus;

/**
 * ${DESCRIPTION}
 * http请求返回类
 * @author fenghong
 * @create 2017-10-11 22:45
 **/
public class HttpResult {

    private int statusCode = HttpStatus.SC_OK;

    //返回数据
    private String responseData;

    //错误信息
    private String errorInfo;

    //异常情况下，类名
    private Class exceptionClass;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Class getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(Class exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getExceptionClassName(){
        if(exceptionClass==null){
            return null;
        }else {
            return exceptionClass.getName();
        }
    }
}
