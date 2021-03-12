package cn.mulanbay.pms.web.bean.request.commonrecord;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CommonRecordTypeFormRequest implements BindUser {

    private Integer id;

    @NotEmpty(message = "{validate.commonRecordType.name.NotEmpty}")
    private String name;

    private Long userId;

    @NotEmpty(message = "{validate.commonRecordType.unit.NotEmpty}")
    private String unit;

    //加入月分析
    @NotNull(message = "{validate.commonRecord.monthStat.NotNull}")
    private Boolean monthStat;

    @NotNull(message = "{validate.commonRecord.yearStat.NotNull}")
    private Boolean yearStat;

    // 加入八小时之外统计
    @NotNull(message = "{validate.commonRecord.overWorkStat.NotNull}")
    private Boolean overWorkStat;

    @NotNull(message = "{validate.commonRecord.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.commonRecord.orderIndex.NotNull}")
    private Short orderIndex;

    @NotNull(message = "{validate.commonRecord.rewardPoint.NotNull}")
    private Integer rewardPoint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getMonthStat() {
        return monthStat;
    }

    public void setMonthStat(Boolean monthStat) {
        this.monthStat = monthStat;
    }

    public Boolean getYearStat() {
        return yearStat;
    }

    public void setYearStat(Boolean yearStat) {
        this.yearStat = yearStat;
    }

    public Boolean getOverWorkStat() {
        return overWorkStat;
    }

    public void setOverWorkStat(Boolean overWorkStat) {
        this.overWorkStat = overWorkStat;
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

    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }
}
