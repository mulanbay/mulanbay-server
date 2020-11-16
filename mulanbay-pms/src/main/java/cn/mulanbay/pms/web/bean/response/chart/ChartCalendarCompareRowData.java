package cn.mulanbay.pms.web.bean.response.chart;

/**
 * 日历图数据（使用与热点图）
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartCalendarCompareRowData {

    private String targetDate;

    private String sourceDate;

    private int days = -1;

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getSourceDate() {
        return sourceDate;
    }

    public void setSourceDate(String sourceDate) {
        this.sourceDate = sourceDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
