package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.domain.UserPlanConfigValue;
import cn.mulanbay.pms.web.bean.response.chart.ChartPieData;

public class PlanReportSummaryStat {

    private long reportCountValueSum;

    private long reportValueSum;

    private long totalCount;

    //相差多少次
    private long diffCountValueSum;

    //相差多少值
    private long diffValueSum;

    private UserPlanConfigValue userPlanConfigValue;

    //统计分析图：乐器练习比例
    private ChartPieData pieData;

    public long getReportCountValueSum() {
        return reportCountValueSum;
    }

    public void setReportCountValueSum(long reportCountValueSum) {
        this.reportCountValueSum = reportCountValueSum;
    }

    public long getReportValueSum() {
        return reportValueSum;
    }

    public void setReportValueSum(long reportValueSum) {
        this.reportValueSum = reportValueSum;
    }

    public ChartPieData getPieData() {
        return pieData;
    }

    public void setPieData(ChartPieData pieData) {
        this.pieData = pieData;
    }

    public void addReportCountValue(long value) {
        reportCountValueSum += value;
    }

    public void addReportValue(long value) {
        reportValueSum += value;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void addTotalCount(long value) {
        totalCount += value;
    }

    public UserPlanConfigValue getUserPlanConfigValue() {
        return userPlanConfigValue;
    }

    public void setUserPlanConfigValue(UserPlanConfigValue userPlanConfigValue) {
        this.userPlanConfigValue = userPlanConfigValue;
    }

    public long getDiffCountValueSum() {
        return diffCountValueSum;
    }

    public void setDiffCountValueSum(long diffCountValueSum) {
        this.diffCountValueSum = diffCountValueSum;
    }

    public long getDiffValueSum() {
        return diffValueSum;
    }

    public void setDiffValueSum(long diffValueSum) {
        this.diffValueSum = diffValueSum;
    }
}
