package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

public class UserRoleDto {

    private BigInteger roleId;

    private String roleName;

    private BigInteger userRoleId;

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public BigInteger getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(BigInteger userRoleId) {
        this.userRoleId = userRoleId;
    }
}
