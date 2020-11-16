package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.LogCompareType;

public class UserScoreGetNextIdRequest implements BindUser {

    private Long userId;

    private Long currentId;

    private LogCompareType compareType;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Long currentId) {
        this.currentId = currentId;
    }

    public LogCompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(LogCompareType compareType) {
        this.compareType = compareType;
    }
}
