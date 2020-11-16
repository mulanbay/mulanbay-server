package cn.mulanbay.pms.web.bean.request.auth;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2018-02-14 15:55
 */
public class RoleFunctionRequest {

    private Long roleId;

    private String functionIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }
}
