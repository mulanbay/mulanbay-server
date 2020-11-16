package cn.mulanbay.pms.web.bean.request.auth;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;

public class SystemFunctionTreeRequest extends QueryBuilder {

    private Boolean needRoot;

    @Query(fieldName = "functionDataType", op = Parameter.Operator.IN)
    private String intFunctionDataTypes;

    @Query(fieldName = "secAuth", op = Parameter.Operator.EQ)
    private Boolean secAuth;

    @Query(fieldName = "permissionAuth", op = Parameter.Operator.EQ)
    private Boolean permissionAuth;

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }

    public String getIntFunctionDataTypes() {
        return intFunctionDataTypes;
    }

    public void setIntFunctionDataTypes(String intFunctionDataTypes) {
        this.intFunctionDataTypes = intFunctionDataTypes;
    }

    public Boolean getSecAuth() {
        return secAuth;
    }

    public void setSecAuth(Boolean secAuth) {
        this.secAuth = secAuth;
    }

    public Boolean getPermissionAuth() {
        return permissionAuth;
    }

    public void setPermissionAuth(Boolean permissionAuth) {
        this.permissionAuth = permissionAuth;
    }
}
