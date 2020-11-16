package cn.mulanbay.pms.web.bean.request.common;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.StatValueType;

public class GetStatValueConfigs implements BindUser {

    private Long fid;
    private StatValueType type;
    private Long userId;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public StatValueType getType() {
        return type;
    }

    public void setType(StatValueType type) {
        this.type = type;
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
