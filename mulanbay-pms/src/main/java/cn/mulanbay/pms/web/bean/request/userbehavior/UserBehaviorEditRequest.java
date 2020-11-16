package cn.mulanbay.pms.web.bean.request.userbehavior;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserBehaviorEditRequest implements BindUser {

    private Long id;

    @NotEmpty(message = "{validate.userBehavior.title.notEmpty}")
    private String title;

    // 当前用户编号
    private Long userId;

    private String bindValues;

    @NotNull(message = "{validate.userBehavior.status.notNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.userBehavior.monthStat.notNull}")
    private Boolean monthStat;

    @NotNull(message = "{validate.userBehavior.dayStat.notNull}")
    private Boolean dayStat;

    @NotNull(message = "{validate.userBehavior.hourStat.notNull}")
    private Boolean hourStat;

    @NotNull(message = "{validate.userBehavior.orderIndex.notNull}")
    @Min(value = 0, message = "{validate.userBehavior.orderIndex.lowerMin}")
    private Short orderIndex;

    private String unit;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getMonthStat() {
        return monthStat;
    }

    public void setMonthStat(Boolean monthStat) {
        this.monthStat = monthStat;
    }

    public Boolean getDayStat() {
        return dayStat;
    }

    public void setDayStat(Boolean dayStat) {
        this.dayStat = dayStat;
    }

    public Boolean getHourStat() {
        return hourStat;
    }

    public void setHourStat(Boolean hourStat) {
        this.hourStat = hourStat;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
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
}
