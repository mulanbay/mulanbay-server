package cn.mulanbay.pms.web.bean.request;

import cn.mulanbay.common.aop.BindUser;

/**
 * 通用的树查询
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class CommonTreeSearch implements BindUser {

    private Boolean needRoot;

    private Long userId;

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
