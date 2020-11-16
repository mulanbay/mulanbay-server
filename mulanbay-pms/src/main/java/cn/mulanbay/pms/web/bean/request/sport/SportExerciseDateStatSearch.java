package cn.mulanbay.pms.web.bean.request.sport;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SportExerciseDateStatSearch extends QueryBuilder implements DateStatSearch, BindUser, FullEndDateTime {

    @Query(fieldName = "exercise_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "exercise_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    @NotNull(message = "{validate.sportExercise.sportTypeId.NotNull}")
    @Query(fieldName = "sport_type_id", op = Parameter.Operator.EQ)
    private Integer sportTypeId;

    private DateGroupType dateGroupType;

    // 是否补全日期
    private Boolean compliteDate;

    //获取最佳的记录（绘制图使用）
    private String bestField;

    // 是否统计更详细的信息
    private Boolean fullStat;

    @Override
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
    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    public Boolean getCompliteDate() {
        return compliteDate;
    }

    @Override
    public Boolean isCompliteDate() {
        return compliteDate;
    }

    public void setCompliteDate(Boolean compliteDate) {
        this.compliteDate = compliteDate;
    }

    public Integer getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(Integer sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBestField() {
        return bestField;
    }

    public void setBestField(String bestField) {
        this.bestField = bestField;
    }

    public Boolean getFullStat() {
        return fullStat;
    }

    public void setFullStat(Boolean fullStat) {
        this.fullStat = fullStat;
    }
}
