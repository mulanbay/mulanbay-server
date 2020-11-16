package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;

public class LifeExperienceSumFormRequest implements BindUser {

    private Long id;

    private Long userId;

    //年份
    @NotNull(message = "{validate.lifeExperienceSum.year.notNull}")
    private Integer year;
    //该年总天数
    @NotNull(message = "{validate.lifeExperienceSum.totalDays.notNull}")
    private Integer totalDays;
    //总工作天数
    @NotNull(message = "{validate.lifeExperienceSum.workDays.notNull}")
    private Integer workDays;
    //旅行天数
    @NotNull(message = "{validate.lifeExperienceSum.travelDays.notNull}")
    private Integer travelDays;
    //学习天数
    @NotNull(message = "{validate.lifeExperienceSum.studyDays.notNull}")
    private Integer studyDays;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Integer workDays) {
        this.workDays = workDays;
    }

    public Integer getTravelDays() {
        return travelDays;
    }

    public void setTravelDays(Integer travelDays) {
        this.travelDays = travelDays;
    }

    public Integer getStudyDays() {
        return studyDays;
    }

    public void setStudyDays(Integer studyDays) {
        this.studyDays = studyDays;
    }
}
