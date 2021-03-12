package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.CompareType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ScoreConfigFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.scoreConfig.name.NotEmpty}")
    private String name;

    @NotNull(message = "{validate.scoreConfig.groupId.NotNull}")
    private Long groupId;

    //sql语句
    @NotEmpty(message = "{validate.scoreConfig.sqlContent.NotEmpty}")
    private String sqlContent;

    @NotNull(message = "{validate.scoreConfig.limitValue.NotNull}")
    private Double limitValue;

    @NotNull(message = "{validate.scoreConfig.compareType.NotNull}")
    private CompareType compareType;

    @NotNull(message = "{validate.scoreConfig.maxScore.NotNull}")
    private Integer maxScore;

    @NotNull(message = "{validate.scoreConfig.status.NotNull}")
    private CommonStatus status;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public Double getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Double limitValue) {
        this.limitValue = limitValue;
    }

    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
