package cn.mulanbay.pms.web.bean.response.chart;

/**
 * 日历饼图明细
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartCalendarPieDetailData {

    private String name;

    private long value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
