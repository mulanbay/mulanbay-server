package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DiaryStat {

    private BigInteger totalCount;

    private BigDecimal totalWords;

    private BigDecimal totalPieces;

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(BigDecimal totalWords) {
        this.totalWords = totalWords;
    }

    public BigDecimal getTotalPieces() {
        return totalPieces;
    }

    public void setTotalPieces(BigDecimal totalPieces) {
        this.totalPieces = totalPieces;
    }
}
