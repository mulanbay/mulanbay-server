package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.QueryBuilder;

public class MusicPracticeTuneLevelStatSearch extends QueryBuilder implements BindUser {

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
