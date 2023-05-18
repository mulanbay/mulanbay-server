package cn.mulanbay.pms.web.bean.request.usercalendar;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

/**
 * @author fenghong
 * @title: UserCalendarGetSourceRequest
 * @description: TODO
 * @date 2023/5/17 22:33
 */
public class UserCalendarGetSourceRequest implements BindUser {

    @NotNull(message = "{validate.bean.id.notNull}")
    private String id;

    private Long userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
