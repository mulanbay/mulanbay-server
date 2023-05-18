package cn.mulanbay.pms.handler.bean;

import cn.mulanbay.pms.persistent.enums.UserCalendarSource;

/**
 * @author fenghong
 * @title: UserCalendarIdBean
 * @description: TODO
 * @date 2023/5/17 22:36
 */
public class UserCalendarIdBean {

    private UserCalendarSource source;

    private Long id;

    public UserCalendarSource getSource() {
        return source;
    }

    public void setSource(UserCalendarSource source) {
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
