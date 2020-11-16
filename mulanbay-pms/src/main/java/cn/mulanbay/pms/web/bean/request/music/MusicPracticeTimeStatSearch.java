package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.web.bean.request.ChartOrderType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class MusicPracticeTimeStatSearch extends QueryBuilder implements DateStatSearch, BindUser, FullEndDateTime {


    @Query(fieldName = "practice_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "practice_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "music_instrument_id", op = Parameter.Operator.EQ)
    private Long musicInstrumentId;

    private DateGroupType dateGroupType;

    // 是否补全日期
    private Boolean compliteDate;

    private ChartType chartType;

    private ChartOrderType chartOrderType;

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
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public DateGroupType getDateGroupType() {
        return dateGroupType;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    @Override
    public Boolean isCompliteDate() {
        return compliteDate;
    }

    public void setCompliteDate(Boolean compliteDate) {
        this.compliteDate = compliteDate;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public ChartOrderType getChartOrderType() {
        return chartOrderType;
    }

    public void setChartOrderType(ChartOrderType chartOrderType) {
        this.chartOrderType = chartOrderType;
    }

    public Long getMusicInstrumentId() {
        return musicInstrumentId;
    }

    public void setMusicInstrumentId(Long musicInstrumentId) {
        this.musicInstrumentId = musicInstrumentId;
    }
}
