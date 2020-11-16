package cn.mulanbay.pms.web.bean.request.auth;

import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.FunctionDataType;
import cn.mulanbay.pms.persistent.enums.FunctionType;
import cn.mulanbay.pms.persistent.enums.UrlType;
import cn.mulanbay.pms.persistent.enums.UserStatus;
import cn.mulanbay.web.bean.request.PageSearch;

public class SystemFunctionSearch extends PageSearch {

    @Query(fieldName = "name,urlAddress,supportMethods", op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "status", op = Parameter.Operator.EQ)
    private UserStatus status;

    @Query(fieldName = "urlType", op = Parameter.Operator.EQ)
    private UrlType urlType;

    @Query(fieldName = "functionType", op = Parameter.Operator.EQ)
    private FunctionType functionType;

    @Query(fieldName = "functionDataType", op = Parameter.Operator.EQ)
    private FunctionDataType functionDataType;

    @Query(fieldName = "supportMethods", op = Parameter.Operator.LIKE)
    private String method;

    @Query(fieldName = "parent.id", op = Parameter.Operator.EQ)
    private Long pid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    public FunctionType getFunctionType() {
        return functionType;
    }

    public void setFunctionType(FunctionType functionType) {
        this.functionType = functionType;
    }

    public FunctionDataType getFunctionDataType() {
        return functionDataType;
    }

    public void setFunctionDataType(FunctionDataType functionDataType) {
        this.functionDataType = functionDataType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
