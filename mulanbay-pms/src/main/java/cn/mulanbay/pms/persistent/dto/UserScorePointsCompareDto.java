package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;

public class UserScorePointsCompareDto {

    private Integer date;

    private BigDecimal score;

    private BigDecimal points;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }
}
