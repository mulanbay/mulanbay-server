package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;
import javax.validation.constraints.NotEmpty;

public class CreateAccountSnapshotRequest implements BindUser {

    private Long userId;

    @NotEmpty(message = "{validate.account.Snapshot.name.notEmpty}")
    private String name;

    private String remark;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
