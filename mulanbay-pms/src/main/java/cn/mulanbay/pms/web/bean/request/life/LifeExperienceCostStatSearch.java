package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.LifeExperienceCostStatType;

import java.util.Date;

public class LifeExperienceCostStatSearch extends QueryBuilder implements FullEndDateTime, BindUser {

    @Query(fieldName = "id", op = Parameter.Operator.EQ)
    private Long lifeExperienceId;

    @Query(fieldName = "start_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "start_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private LifeExperienceCostStatType statType;

    public Long getLifeExperienceId() {
        return lifeExperienceId;
    }

    public void setLifeExperienceId(Long lifeExperienceId) {
        this.lifeExperienceId = lifeExperienceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LifeExperienceCostStatType getStatType() {
        return statType;
    }

    public void setStatType(LifeExperienceCostStatType statType) {
        this.statType = statType;
    }

}
