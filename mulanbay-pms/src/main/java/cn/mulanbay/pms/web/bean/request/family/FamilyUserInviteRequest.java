package cn.mulanbay.pms.web.bean.request.family;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FamilyUserInviteRequest {

    @NotEmpty(message = "{validate.familyUser.username.notEmpty}")
    private String username;

    @NotNull(message = "{validate.familyUser.familyId.notNull}")
    private Long familyId;

    //别名
    private String aliasName;

    //是否管理员
    @NotNull(message = "{validate.familyUser.admin.notNull}")
    private Boolean admin;

    private String remark;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
