package cn.mulanbay.pms.web.bean.request.system;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class CommandSendRequest implements BindUser {

    @NotNull(message = "{validate.command.id.NotNull}")
    private Long id;

    private boolean sync;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
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
