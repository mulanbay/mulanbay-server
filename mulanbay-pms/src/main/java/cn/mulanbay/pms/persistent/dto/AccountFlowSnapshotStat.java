package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;

public class AccountFlowSnapshotStat {

    private String bussKey;

    private BigDecimal beforeAmount;

    private BigDecimal afterAmount;

    public String getBussKey() {
        return bussKey;
    }

    public void setBussKey(String bussKey) {
        this.bussKey = bussKey;
    }

    public BigDecimal getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(BigDecimal beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public BigDecimal getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(BigDecimal afterAmount) {
        this.afterAmount = afterAmount;
    }
}
