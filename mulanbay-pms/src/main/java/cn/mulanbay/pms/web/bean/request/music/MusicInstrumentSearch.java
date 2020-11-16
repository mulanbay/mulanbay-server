package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class MusicInstrumentSearch extends PageSearch implements BindUser {

    @Query(fieldName = "name", op = Parameter.Operator.EQ)
    private String name;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    private Boolean needRoot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }
}
