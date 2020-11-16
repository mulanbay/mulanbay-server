package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class UserPlanConfigValueFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotNull(message = "{validate.userPlanConfigValue.userPlanId.NotNull}")
    private Long userPlanId;

    @NotNull(message = "{validate.userPlanConfigValue.year.NotNull}")
    private Integer year;
    //次数值
    @NotNull(message = "{validate.userPlanConfigValue.planCountValue.NotNull}")
    private Long planCountValue;
    //值，比如购买金额，锻炼时间
    @NotNull(message = "{validate.userPlanConfigValue.planValue.NotNull}")
    private Long planValue;
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

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getPlanCountValue() {
        return planCountValue;
    }

    public void setPlanCountValue(Long planCountValue) {
        this.planCountValue = planCountValue;
    }

    public Long getPlanValue() {
        return planValue;
    }

    public void setPlanValue(Long planValue) {
        this.planValue = planValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
