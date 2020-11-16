package cn.mulanbay.pms.web.bean.request.report;

import cn.mulanbay.common.aop.BindUser;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserReportRemindFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.userReportRemind.userReportConfigId.NotNull}")
    private Long userReportConfigId;

    //提醒时间
    @NotEmpty(message = "{validate.userReportRemind.remindTime.notEmpty}")
    private String remindTime;
    private String remark;

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

    public Long getUserReportConfigId() {
        return userReportConfigId;
    }

    public void setUserReportConfigId(Long userReportConfigId) {
        this.userReportConfigId = userReportConfigId;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
