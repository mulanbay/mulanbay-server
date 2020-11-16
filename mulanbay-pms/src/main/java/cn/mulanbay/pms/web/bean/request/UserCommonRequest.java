package cn.mulanbay.pms.web.bean.request;

import cn.mulanbay.common.aop.BindUser;

/**
 * 通用用户请求
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class UserCommonRequest implements BindUser {

    private Long userId;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
