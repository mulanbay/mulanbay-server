package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;

public class LifeExperienceTreeSearch implements BindUser {

    private Boolean needRoot;

    public Long userId;

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
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
