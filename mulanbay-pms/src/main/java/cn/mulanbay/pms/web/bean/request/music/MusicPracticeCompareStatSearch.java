package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class MusicPracticeCompareStatSearch extends QueryBuilder implements DateStatSearch, BindUser, FullEndDateTime {


    @Query(fieldName = "practice_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "practice_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    private Long userId;

    @NotNull(message = "{validate.musicPractice.musicInstrumentIds.size}")
    @Size(min = 2, message = "{validate.musicPractice.musicInstrumentIds.size}")
    private List<Long> musicInstrumentIds;

    private DateGroupType xgroupType;

    private DateGroupType ygroupType;

    public DateGroupType getXgroupType() {
        return xgroupType;
    }

    public void setXgroupType(DateGroupType xgroupType) {
        this.xgroupType = xgroupType;
    }

    public DateGroupType getYgroupType() {
        return ygroupType;
    }

    public void setYgroupType(DateGroupType ygroupType) {
        this.ygroupType = ygroupType;
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

    public List<Long> getMusicInstrumentIds() {
        return musicInstrumentIds;
    }

    public void setMusicInstrumentIds(List<Long> musicInstrumentIds) {
        this.musicInstrumentIds = musicInstrumentIds;
    }
}
