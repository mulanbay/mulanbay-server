package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;

public class PlanReportPlanCommendDto {

    private BigDecimal reportCountValue;

    private BigDecimal reportValue;

    public BigDecimal getReportCountValue() {
        return reportCountValue;
    }

    public void setReportCountValue(BigDecimal reportCountValue) {
        this.reportCountValue = reportCountValue;
    }

    public BigDecimal getReportValue() {
        return reportValue;
    }

    public void setReportValue(BigDecimal reportValue) {
        this.reportValue = reportValue;
    }
}
