package cn.mulanbay.pms.web.bean.request.sport;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class SportMilestoneFormRequest implements BindUser {

    private Long id;

    @NotEmpty(message = "{validate.sportMilestone.name.notEmpty}")
    private String name;

    private String alais;

    private Long userId;

    @NotNull(message = "{validate.sportMilestone.sportTypeId.NotNull}")
    private Integer sportTypeId;

    @NotNull(message = "{validate.sportMilestone.kilometres.NotNull}")
    private Double kilometres;

    @NotNull(message = "{validate.sportMilestone.minutes.NotNull}")
    private Integer minutes;

    private Date fromExerciseDate;
    private Date toExerciseDate;
    private Integer costDays;

    @NotNull(message = "{validate.sportMilestone.orderIndex.NotNull}")
    private Short orderIndex;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlais() {
        return alais;
    }

    public void setAlais(String alais) {
        this.alais = alais;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(Integer sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Date getFromExerciseDate() {
        return fromExerciseDate;
    }

    public void setFromExerciseDate(Date fromExerciseDate) {
        this.fromExerciseDate = fromExerciseDate;
    }

    public Date getToExerciseDate() {
        return toExerciseDate;
    }

    public void setToExerciseDate(Date toExerciseDate) {
        this.toExerciseDate = toExerciseDate;
    }

    public Integer getCostDays() {
        return costDays;
    }

    public void setCostDays(Integer costDays) {
        this.costDays = costDays;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
