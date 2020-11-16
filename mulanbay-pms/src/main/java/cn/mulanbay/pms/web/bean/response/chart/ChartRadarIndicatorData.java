package cn.mulanbay.pms.web.bean.response.chart;

/**
 * 雷达图的数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartRadarIndicatorData {

    private String text;

    private long max;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }
}
