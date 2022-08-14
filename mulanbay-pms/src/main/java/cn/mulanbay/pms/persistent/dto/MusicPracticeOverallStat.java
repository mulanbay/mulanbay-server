package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MusicPracticeOverallStat {

    private Integer indexValue;
    private BigInteger totalCount;
    private BigDecimal totalMinutes;
    private BigInteger musicInstrumentId;

    public Integer getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Integer indexValue) {
        this.indexValue = indexValue;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(BigDecimal totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public BigInteger getMusicInstrumentId() {
        return musicInstrumentId;
    }

    public void setMusicInstrumentId(BigInteger musicInstrumentId) {
        this.musicInstrumentId = musicInstrumentId;
    }
}
