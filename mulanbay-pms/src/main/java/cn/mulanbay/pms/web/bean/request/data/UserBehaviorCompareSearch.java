package cn.mulanbay.pms.web.bean.request.data;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class UserBehaviorCompareSearch implements BindUser {

    private Integer year;

    public Long userId;

    //源查询关键字
    private String sourceName;

    @NotNull(message = "{validate.userBehavior.sourceUserBehaviorId.notNull}")
    private Long sourceUserBehaviorId;

    //目标（比对的）
    private String targetName;

    @NotNull(message = "{validate.userBehavior.targetUserBehaviorId.notNull}")
    private Long targetUserBehaviorId;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Long getSourceUserBehaviorId() {
        return sourceUserBehaviorId;
    }

    public void setSourceUserBehaviorId(Long sourceUserBehaviorId) {
        this.sourceUserBehaviorId = sourceUserBehaviorId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Long getTargetUserBehaviorId() {
        return targetUserBehaviorId;
    }

    public void setTargetUserBehaviorId(Long targetUserBehaviorId) {
        this.targetUserBehaviorId = targetUserBehaviorId;
    }
}
