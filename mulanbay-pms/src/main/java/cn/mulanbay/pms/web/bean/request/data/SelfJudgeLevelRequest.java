package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class SelfJudgeLevelRequest implements BindUser {

    private Long userId;

    @NotNull(message = "{validate.levelConfig.updateLevel.NotNull}")
    private Boolean updateLevel;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getUpdateLevel() {
        return updateLevel;
    }

    public void setUpdateLevel(Boolean updateLevel) {
        this.updateLevel = updateLevel;
    }
}
