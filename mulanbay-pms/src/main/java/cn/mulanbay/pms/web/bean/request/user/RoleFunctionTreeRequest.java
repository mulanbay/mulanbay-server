package cn.mulanbay.pms.web.bean.request.user;

public class RoleFunctionTreeRequest {

    private Boolean needRoot;

    private Long roleId;

    //是否要把已经包含的权限和权限树数据分开，主要是为了和以前的版本兼容
    private Boolean separate;

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Boolean getSeparate() {
        return separate;
    }

    public void setSeparate(Boolean separate) {
        this.separate = separate;
    }
}
