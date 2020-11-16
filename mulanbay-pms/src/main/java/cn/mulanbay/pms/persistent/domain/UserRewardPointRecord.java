package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.RewardSource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户积分奖励记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_reward_point_record")
public class UserRewardPointRecord implements Serializable {

    private static final long serialVersionUID = 2614339809900964946L;
    private Long id;
    private Long userId;
    private Integer rewards;
    private Long sourceId;
    private RewardSource rewardSource;
    //奖励后的积分
    private Integer afterPoints;
    private Long messageId;
    private Date createdTime;
    private String remark;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "rewards")
    public Integer getRewards() {
        return rewards;
    }

    public void setRewards(Integer rewards) {
        this.rewards = rewards;
    }

    @Basic
    @Column(name = "source_id")
    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Basic
    @Column(name = "reward_source")
    public RewardSource getRewardSource() {
        return rewardSource;
    }

    public void setRewardSource(RewardSource rewardSource) {
        this.rewardSource = rewardSource;
    }

    @Basic
    @Column(name = "after_points")
    public Integer getAfterPoints() {
        return afterPoints;
    }

    public void setAfterPoints(Integer afterPoints) {
        this.afterPoints = afterPoints;
    }

    @Basic
    @Column(name = "message_id")
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
