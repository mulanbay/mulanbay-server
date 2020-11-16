package cn.mulanbay.pms.web.bean.request.data;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class JudgeLevelRequest {

    @NotEmpty(message = "{validate.levelConfig.username.NotEmpty}")
    private String username;

    @NotNull(message = "{validate.levelConfig.updateLevel.NotNull}")
    private Boolean updateLevel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getUpdateLevel() {
        return updateLevel;
    }

    public void setUpdateLevel(Boolean updateLevel) {
        this.updateLevel = updateLevel;
    }
}
