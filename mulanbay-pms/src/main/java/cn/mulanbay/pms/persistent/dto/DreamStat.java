package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DreamStat {

    private String name;

    private Integer difficulty;
    private BigDecimal importantLevel;
    private Short status;
    //次数
    private BigInteger totalCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public BigDecimal getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(BigDecimal importantLevel) {
        this.importantLevel = importantLevel;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }
}
