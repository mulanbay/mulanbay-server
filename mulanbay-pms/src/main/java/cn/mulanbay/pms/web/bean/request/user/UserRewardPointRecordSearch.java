package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.ReferType;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class UserRewardPointRecordSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "createdTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "createdTime", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "rewardSource", op = Parameter.Operator.EQ)
    private RewardSource rewardSource;

    @Query(fieldName = "rewards", op = Parameter.Operator.REFER, referFieldName = "rewardsCompareType", referType = ReferType.OP_REFER)
    private Integer rewards;

    @Query(fieldName = "sourceId", op = Parameter.Operator.EQ)
    private Long sourceId;

    //奖励积分的比较类型（大于，还是小于）
    private Parameter.Operator rewardsCompareType;

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

    public RewardSource getRewardSource() {
        return rewardSource;
    }

    public void setRewardSource(RewardSource rewardSource) {
        this.rewardSource = rewardSource;
    }

    public Integer getRewards() {
        return rewards;
    }

    public void setRewards(Integer rewards) {
        this.rewards = rewards;
    }

    public Parameter.Operator getRewardsCompareType() {
        return rewardsCompareType;
    }

    public void setRewardsCompareType(Parameter.Operator rewardsCompareType) {
        this.rewardsCompareType = rewardsCompareType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
}
