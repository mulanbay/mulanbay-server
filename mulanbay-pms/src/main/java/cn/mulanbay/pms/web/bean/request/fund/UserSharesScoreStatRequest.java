package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.WardType;

public class UserSharesScoreStatRequest implements BindUser {

    private Long userSharesId;

    private Long userId;

    //价格记录的ID
    private Long id;

    private WardType type;

    public Long getUserSharesId() {
        return userSharesId;
    }

    public void setUserSharesId(Long userSharesId) {
        this.userSharesId = userSharesId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WardType getType() {
        return type;
    }

    public void setType(WardType type) {
        this.type = type;
    }
}
