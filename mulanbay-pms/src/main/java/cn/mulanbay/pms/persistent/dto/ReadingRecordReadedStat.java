package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieData;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ReadingRecordReadedStat {

    private BigInteger totalCount;

    private BigDecimal totalCostDays;

    //统计分析图：乐器练习比例
    private ChartPieData pieData;

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalCostDays() {
        return totalCostDays;
    }

    public void setTotalCostDays(BigDecimal totalCostDays) {
        this.totalCostDays = totalCostDays;
    }

    /**
     * 获取平均花费天数
     *
     * @return
     */
    public double getAvgCostDays() {
        if (totalCount == null || totalCount.intValue() == 0) {
            return 0;
        } else if (totalCostDays == null || totalCostDays.doubleValue() <= 0) {
            return 0;
        } else {
            return NumberUtil.getAverageValue(totalCostDays.doubleValue(), totalCount.intValue(), 1);
        }
    }

    public ChartPieData getPieData() {
        return pieData;
    }

    public void setPieData(ChartPieData pieData) {
        this.pieData = pieData;
    }
}
