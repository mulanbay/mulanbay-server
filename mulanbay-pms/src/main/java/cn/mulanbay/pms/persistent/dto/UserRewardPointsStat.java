package cn.mulanbay.pms.persistent.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UserRewardPointsStat implements Serializable {

    private String dateStr;

    private BigInteger userId;

    //总次数
    private BigInteger totalCount;

    // 总得分
    private BigDecimal totalPoints;

    private BigDecimal currentPoints;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(BigDecimal totalPoints) {
        this.totalPoints = totalPoints;
    }

    public BigDecimal getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(BigDecimal currentPoints) {
        this.currentPoints = currentPoints;
    }
}
