package cn.mulanbay.pms.web.bean.request.family;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class FamilyUserJoinRequest implements BindUser {

    @NotNull(message = "{validate.familyUser.id.notNull}")
    private Long id;

    private Long userId;

    //是否加入
    @NotNull(message = "{validate.familyUser.join.notNull}")
    private Boolean join;

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

    public Boolean getJoin() {
        return join;
    }

    public void setJoin(Boolean join) {
        this.join = join;
    }
}
