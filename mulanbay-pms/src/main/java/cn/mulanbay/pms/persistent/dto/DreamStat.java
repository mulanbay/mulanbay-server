package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DreamStat {

    private String name;

    private Integer difficulty;
    private BigDecimal important_level;
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

    public BigDecimal getImportant_level() {
        return important_level;
    }

    public void setImportant_level(BigDecimal important_level) {
        this.important_level = important_level;
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
