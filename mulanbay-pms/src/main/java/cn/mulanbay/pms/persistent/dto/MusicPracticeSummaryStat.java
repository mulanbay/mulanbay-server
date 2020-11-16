package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.web.bean.response.chart.ChartPieData;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MusicPracticeSummaryStat {

    private BigInteger totalCount;

    private BigDecimal totalMinutes;

    private double averageMinutes;

    //统计分析图：乐器练习比例
    private ChartPieData pieData;

    public double getAverageMinutes() {
        return averageMinutes;
    }

    public void setAverageMinutes(double averageMinutes) {
        this.averageMinutes = averageMinutes;
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

    public ChartPieData getPieData() {
        return pieData;
    }

    public void setPieData(ChartPieData pieData) {
        this.pieData = pieData;
    }
}
