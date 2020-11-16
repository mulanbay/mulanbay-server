package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class MusicPracticeStatSearch extends PageSearch implements DateStatSearch, BindUser, FullEndDateTime {

    @Query(fieldName = "minutes", op = Parameter.Operator.GTE)
    private Integer minMinutes;

    @Query(fieldName = "minutes", op = Parameter.Operator.LTE)
    private Integer maxMinutes;

    @Query(fieldName = "practice_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "practice_date", op = Parameter.Operator.LTE)
    private Date endDate;


    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "music_instrument_id", op = Parameter.Operator.EQ)
    public Long musicInstrumentId;

    public Integer getMinMinutes() {
        return minMinutes;
    }

    public void setMinMinutes(Integer minMinutes) {
        this.minMinutes = minMinutes;
    }

    public Integer getMaxMinutes() {
        return maxMinutes;
    }

    public void setMaxMinutes(Integer maxMinutes) {
        this.maxMinutes = maxMinutes;
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

    public Long getMusicInstrumentId() {
        return musicInstrumentId;
    }

    public void setMusicInstrumentId(Long musicInstrumentId) {
        this.musicInstrumentId = musicInstrumentId;
    }
}
