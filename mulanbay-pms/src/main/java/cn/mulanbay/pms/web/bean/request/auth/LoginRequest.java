package cn.mulanbay.pms.web.bean.request.auth;

import cn.mulanbay.pms.persistent.enums.FamilyMode;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotEmpty(message = "{validate.user.username.notNull}")
    private String username;

    @NotEmpty(message = "{validate.user.password.notNull}")
    private String password;

    @NotEmpty(message = "{validate.user.uuid.notNull}")
    private String uuid;

    @NotEmpty(message = "{validate.user.code.notNull}")
    private String code;

    //家庭模式
    private FamilyMode familyMode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FamilyMode getFamilyMode() {
        return familyMode;
    }

    public void setFamilyMode(FamilyMode familyMode) {
        this.familyMode = familyMode;
    }
}
