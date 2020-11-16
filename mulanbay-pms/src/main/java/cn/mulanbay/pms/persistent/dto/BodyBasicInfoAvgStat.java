package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;

public class BodyBasicInfoAvgStat {

    private BigDecimal avgWeight;
    private BigDecimal avgHeight;

    public BigDecimal getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(BigDecimal avgWeight) {
        this.avgWeight = avgWeight;
    }

    public BigDecimal getAvgHeight() {
        return avgHeight;
    }

    public void setAvgHeight(BigDecimal avgHeight) {
        this.avgHeight = avgHeight;
    }
}
