package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.TreatStage;
import cn.mulanbay.pms.persistent.enums.TreatType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class TreatRecordSearch extends PageSearch implements DateStatSearch, BindUser {

    // 支持多个字段查询
    @Query(fieldName = "hospital,department,organ,disease,diagnosedDisease,tags", op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String name;

    @Query(fieldName = "tags", op = Parameter.Operator.EQ)
    private String tags;

    @Query(fieldName = "treatDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "treatDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "isSick", op = Parameter.Operator.EQ)
    private Boolean sick;

    @Query(fieldName = "treatType", op = Parameter.Operator.EQ)
    private TreatType treatType;

    @Query(fieldName = "stage", op = Parameter.Operator.EQ)
    private TreatStage stage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

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
    public DateGroupType getDateGroupType() {
        return null;
    }

    @Override
    public Boolean isCompliteDate() {
        return null;
    }

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

    public Boolean getSick() {
        return sick;
    }

    public void setSick(Boolean sick) {
        this.sick = sick;
    }

    public TreatType getTreatType() {
        return treatType;
    }

    public void setTreatType(TreatType treatType) {
        this.treatType = treatType;
    }

    public TreatStage getStage() {
        return stage;
    }

    public void setStage(TreatStage stage) {
        this.stage = stage;
    }
}
