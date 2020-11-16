package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.ExperienceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class LifeExperienceFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotEmpty(message = "{validate.lifeExperience.name.notEmpty}")
    private String name;

    @NotNull(message = "{validate.lifeExperience.type.NotNull}")
    private ExperienceType type;

    @NotNull(message = "{validate.lifeExperience.days.NotNull}")
    private Integer days;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.lifeExperience.startDate.NotNull}")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.lifeExperience.endDate.NotNull}")
    private Date endDate;

    private Double cost;

    private String tags;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExperienceType getType() {
        return type;
    }

    public void setType(ExperienceType type) {
        this.type = type;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
