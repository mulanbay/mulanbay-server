package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class BuyRecordDeleteChildrenRequest implements BindUser {

    @NotNull(message = "{validate.buyRecord.pid.notNull}")
    private Long pid;

    private Long userId;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
