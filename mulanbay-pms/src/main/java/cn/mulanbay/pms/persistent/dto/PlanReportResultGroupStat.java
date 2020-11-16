package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

public class PlanReportResultGroupStat {

    private BigInteger totalCount;

    private Short resultType;

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public Short getResultType() {
        return resultType;
    }

    public void setResultType(Short resultType) {
        this.resultType = resultType;
    }
}
