package cn.mulanbay.pms.web.bean.request.dream;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.DreamStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DreamFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotEmpty(message = "{validate.dream.name.notEmpty}")
    private String name;
    private Integer minMoney;
    private Integer maxMoney;

    @NotNull(message = "{validate.dream.difficulty.NotNull}")
    private Integer difficulty;

    @NotNull(message = "{validate.dream.importantLevel.NotNull}")
    private Double importantLevel;

    @NotNull(message = "{validate.dream.expectDays.NotNull}")
    private Integer expectDays;

    @NotNull(message = "{validate.dream.status.NotNull}")
    private DreamStatus status;

    private Integer rate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.dream.proposedDate.NotNull}")
    private Date proposedDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.dream.deadline.NotNull}")
    private Date deadline;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishedDate;
    //延期次数
    private Integer delays;

    //针对proposedDate的改变历史，格式:2017-01-01-->2017-03-02
    private String dateChangeHistory;

    //关联的计划配置项，到时根据无时间条件来计算进度
    private Long userPlanId;
    //计划值(小时、天、次数等等)
    private Long planValue;

    private Boolean remind;

    private Integer rewardPoint;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Integer minMoney) {
        this.minMoney = minMoney;
    }

    public Integer getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Integer maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Double getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(Double importantLevel) {
        this.importantLevel = importantLevel;
    }

    public Integer getExpectDays() {
        return expectDays;
    }

    public void setExpectDays(Integer expectDays) {
        this.expectDays = expectDays;
    }

    public DreamStatus getStatus() {
        return status;
    }

    public void setStatus(DreamStatus status) {
        this.status = status;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public Integer getDelays() {
        return delays;
    }

    public void setDelays(Integer delays) {
        this.delays = delays;
    }

    public String getDateChangeHistory() {
        return dateChangeHistory;
    }

    public void setDateChangeHistory(String dateChangeHistory) {
        this.dateChangeHistory = dateChangeHistory;
    }

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public Long getPlanValue() {
        return planValue;
    }

    public void setPlanValue(Long planValue) {
        this.planValue = planValue;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public Integer getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Integer rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
