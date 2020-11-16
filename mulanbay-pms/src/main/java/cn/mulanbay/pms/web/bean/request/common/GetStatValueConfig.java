package cn.mulanbay.pms.web.bean.request.common;

import cn.mulanbay.common.aop.BindUser;

public class GetStatValueConfig implements BindUser {

    private Long id;
    private String pid;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
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
