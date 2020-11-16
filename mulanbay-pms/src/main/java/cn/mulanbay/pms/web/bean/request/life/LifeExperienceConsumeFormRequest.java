package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class LifeExperienceConsumeFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotEmpty(message = "{validate.lifeExperienceConsume.name.notEmpty}")
    private String name;

    @NotNull(message = "{validate.lifeExperienceConsume.lifeExperienceDetailId.NotNull}")
    private Long lifeExperienceDetailId;

    @NotNull(message = "{validate.lifeExperienceConsume.consumeTypeId.NotNull}")
    private Integer consumeTypeId;

    private Double cost;

    //有些花费可以直接与消费记录挂钩
    private Long buyRecordId;

    // 是否加入统计
    @NotNull(message = "{validate.lifeExperienceConsume.statable.NotNull}")
    private Boolean statable;

    private Integer roundDays;

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

    public Long getLifeExperienceDetailId() {
        return lifeExperienceDetailId;
    }

    public void setLifeExperienceDetailId(Long lifeExperienceDetailId) {
        this.lifeExperienceDetailId = lifeExperienceDetailId;
    }

    public Integer getConsumeTypeId() {
        return consumeTypeId;
    }

    public void setConsumeTypeId(Integer consumeTypeId) {
        this.consumeTypeId = consumeTypeId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getBuyRecordId() {
        return buyRecordId;
    }

    public void setBuyRecordId(Long buyRecordId) {
        this.buyRecordId = buyRecordId;
    }

    public Boolean getStatable() {
        return statable;
    }

    public void setStatable(Boolean statable) {
        this.statable = statable;
    }

    public Integer getRoundDays() {
        return roundDays;
    }

    public void setRoundDays(Integer roundDays) {
        this.roundDays = roundDays;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
