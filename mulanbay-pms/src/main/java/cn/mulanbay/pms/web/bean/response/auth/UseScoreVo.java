package cn.mulanbay.pms.web.bean.response.auth;

import cn.mulanbay.pms.persistent.enums.CompareType;

public class UseScoreVo {

    private String name;

    private Long scoreConfigId;

    private double statValue;

    private double limitValue;

    private CompareType compareType;

    private int score;

    private int maxScore;

    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getScoreConfigId() {
        return scoreConfigId;
    }

    public void setScoreConfigId(Long scoreConfigId) {
        this.scoreConfigId = scoreConfigId;
    }

    public double getStatValue() {
        return statValue;
    }

    public void setStatValue(double statValue) {
        this.statValue = statValue;
    }

    public double getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(double limitValue) {
        this.limitValue = limitValue;
    }

    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
