package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;

public class UserScorePointsCompareDto {

    private Number date;

    private BigDecimal score;

    private BigDecimal points;

    public Number getDate() {
        return date;
    }

    public void setDate(Number date) {
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
