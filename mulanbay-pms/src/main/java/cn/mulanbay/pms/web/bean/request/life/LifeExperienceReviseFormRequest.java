package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class LifeExperienceReviseFormRequest implements BindUser {

    @NotNull(message = "{validate.lifeExperienceSum.id.notNull}")
    private Long id;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
