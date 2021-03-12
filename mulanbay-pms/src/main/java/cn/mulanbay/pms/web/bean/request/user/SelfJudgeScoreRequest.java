package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotEmpty;

public class SelfJudgeScoreRequest implements BindUser {

    private Long userId;

    @NotEmpty(message = "{validate.userScore.groupCode.NotEmpty}")
    private String groupCode;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
