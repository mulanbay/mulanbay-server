package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.BindUserLevel;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserPlanFormRequest implements BindUser, BindUserLevel {

    private Long id;
    private Long userId;

    @NotNull(message = "{validate.userPlan.planConfigId.NotNull}")
    private Long planConfigId;

    @NotEmpty(message = "{validate.userPlan.title.notEmpty}")
    private String title;
    //用户自己选择的值
    private String bindValues;

    @NotNull(message = "{validate.userPlan.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.userPlan.orderIndex.NotNull}")
    private Short orderIndex;
    //第一天统计日期(因为很多时候每个人，不同业务模块的开始记录时间不一样)
    private Date firstStatDay;

    @NotNull(message = "{validate.userPlan.remind.NotNull}")
    private Boolean remind;

    private String calendarTitle;

    private String calendarTime;

    private String unit;

    private String remark;

    private Integer level;

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

    public Long getPlanConfigId() {
        return planConfigId;
    }

    public void setPlanConfigId(Long planConfigId) {
        this.planConfigId = planConfigId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Date getFirstStatDay() {
        return firstStatDay;
    }

    public void setFirstStatDay(Date firstStatDay) {
        this.firstStatDay = firstStatDay;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public String getCalendarTitle() {
        return calendarTitle;
    }

    public void setCalendarTitle(String calendarTitle) {
        this.calendarTitle = calendarTitle;
    }

    public String getCalendarTime() {
        return calendarTime;
    }

    public void setCalendarTime(String calendarTime) {
        this.calendarTime = calendarTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }
}
