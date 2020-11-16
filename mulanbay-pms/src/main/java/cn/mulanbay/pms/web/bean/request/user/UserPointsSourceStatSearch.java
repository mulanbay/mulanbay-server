package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.web.bean.request.CommonOrderType;
import cn.mulanbay.pms.web.bean.request.DateStatSearch;

import java.util.Date;

public class UserPointsSourceStatSearch extends QueryBuilder implements BindUser, FullEndDateTime, DateStatSearch {

    @Query(fieldName = "created_time", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "created_time", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "user_id", op = Parameter.Operator.EQ)
    public Long userId;

    private RewardSource rewardSource;

    //todo sql模式下，枚举值参数绑定有点问题，未解决，临时转换下
    @Query(fieldName = "reward_source", op = Parameter.Operator.EQ)
    private Integer rewardSourceInt;

    private DateGroupType dateGroupType;

    // 是否补全日期
    private Boolean compliteDate;

    private CommonOrderType orderBy;

    private ChartType chartType;

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

    @Override
    public Boolean isCompliteDate() {
        return compliteDate;
    }

    public void setDateGroupType(DateGroupType dateGroupType) {
        this.dateGroupType = dateGroupType;
    }

    public Boolean getCompliteDate() {
        return compliteDate;
    }

    public void setCompliteDate(Boolean compliteDate) {
        this.compliteDate = compliteDate;
    }


    public RewardSource getRewardSource() {
        return rewardSource;
    }

    public void setRewardSource(RewardSource rewardSource) {
        this.rewardSource = rewardSource;
    }

    public Integer getRewardSourceInt() {
        return rewardSource == null ? null : rewardSource.ordinal();
    }

    public void setRewardSourceInt(Integer rewardSourceInt) {
        this.rewardSourceInt = rewardSourceInt;
    }

    public void setRewardSourceIntByRewardSource(RewardSource rewardSource) {
        if (rewardSource != null) {
            this.rewardSourceInt = rewardSource.getValue();
        }
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public CommonOrderType getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(CommonOrderType orderBy) {
        this.orderBy = orderBy;
    }
}
