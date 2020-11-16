package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UserPointsSourceStat {

    private BigInteger sourceId;

    private Integer rewards;

    private Short rewardSource;

    private BigDecimal totalRewardPoints;

    private BigInteger totalCount;

    public BigInteger getSourceId() {
        return sourceId;
    }

    public void setSourceId(BigInteger sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getRewards() {
        return rewards;
    }

    public void setRewards(Integer rewards) {
        this.rewards = rewards;
    }

    public Short getRewardSource() {
        return rewardSource;
    }

    public void setRewardSource(Short rewardSource) {
        this.rewardSource = rewardSource;
    }

    public BigDecimal getTotalRewardPoints() {
        return totalRewardPoints;
    }

    public void setTotalRewardPoints(BigDecimal totalRewardPoints) {
        this.totalRewardPoints = totalRewardPoints;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }
}
