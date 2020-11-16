package cn.mulanbay.pms.web.bean.response.chart;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日历图数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartCalendarData extends BaseChartData {

    private int year;

    private String[] legendData;

    //最大值、最小值用于界面计算每个点的大小使用，目前没有使用
    private double minValue;

    private double maxValue;

    private String unit;

    //元素的条数，时间区域类的，多天的只算一次
    private int count;

    //天的条数，时间区域类的，几天算几条
    private int daysCount;

    //统计最前面的几个，默认不显示
    private int top = 0;

    private double totalValue;

    //自定义数据
    private Object customData;

    //数据类型：['2016-01-01',111]
    private List<Object[]> series = new ArrayList<>();

    //绘图使用，比如锻炼管理可以绘制里程碑，数据结构是：o[0]2017-01-01，o[1]值
    private List<Object[]> graphData = new ArrayList<>();

    public void addSerieData(Date date, Object value) {
        this.addSerieData(new Object[]{DateUtil.getFormatDate(date, DateUtil.FormatDay1), value});
    }

    public void addSerieData(String dateString, Object value) {
        this.addSerieData(new Object[]{dateString, value});
    }

    /**
     * 数据格式：['2016-01-01',111]
     *
     * @param data
     */
    public void addSerieData(Object[] data) {
        Object[] os = null;
        double vv = Double.valueOf(data[1].toString());
        for (Object[] dd : series) {
            //如果已经包含，则累计
            if (dd[0].toString().equals(data[0].toString())) {
                dd[1] = vv + Double.valueOf(dd[1].toString());
                os = dd;
            }
        }
        if (os == null) {
            series.add(data);
        }
        // 计算最小最大值
        if (vv > maxValue) {
            maxValue = vv;
        }
        if (vv < minValue || minValue == 0) {
            //第一次或者比其小
            minValue = vv;
        }
        daysCount++;
        totalValue += vv;
    }

    /**
     * 如果是时间区域，说明需要根据天数来一直增加，
     * 比如旅行，开始日期：2017-01-01，一共三天，那么需要保存：2017-01-01,2017-01-02,2017-01-03
     *
     * @param dateString
     * @param value
     * @param dateRegion
     * @param days
     */
    public void addSerieData(String dateString, double value, boolean dateRegion, int days) {
        if (!dateRegion) {
            this.addSerieData(new Object[]{dateString, value});
        } else {
            Date startDate = DateUtil.getDate(dateString, DateUtil.FormatDay1);
            for (int i = 0; i < days; i++) {
                String newDateString = DateUtil.getFormatDate(DateUtil.getDate(i, startDate), DateUtil.FormatDay1);
                //平均下去了，所以每个只是1
                this.addSerieData(new Object[]{newDateString, 1});
            }
        }
    }

    public String[] getLegendData() {
        return legendData;
    }

    public void setLegendData(String[] legendData) {
        this.legendData = legendData;
    }

    public void setLegendData(String cateTitle, int top) {
        this.top = top;
        this.legendData = new String[]{cateTitle, "前" + top + "名"};
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public List<Object[]> getSeries() {
        return series;
    }

    public void setSeries(List<Object[]> series) {
        this.series = series;
    }

    public String[] getRangeFirst() {
        return new String[]{year + "-01-01", year + "-06-30"};
    }

    public String[] getRangeSecond() {
        return new String[]{year + "-07-01", year + "-12-31"};
    }

    public double getCompareSizeValue() {
        //视图中的symbolSize要处于1-10之间
        return 1;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String getSubTitle() {
        int yearDays = DateUtil.getDays(year);
        String ss = super.getSubTitle();
        if (ss == null) {
            ss = "";
        }
        ss += "总次数:" + count + ",总值:" + totalValue + unit + ",总点数:" + daysCount + " (全年占比" + NumberUtil.getPercentValue(daysCount, yearDays, 1) + "%)";
        int size = graphData.size();
        if (size > 0) {
            ss += ",所选跟踪项总点数:" + size;
        }
        return ss;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(Object customData) {
        this.customData = customData;
    }

    public List<Object[]> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<Object[]> graphData) {
        this.graphData = graphData;
    }

    public void addGraph(Date date, Object vv) {
        this.graphData.add(new Object[]{DateUtil.getFormatDate(date, DateUtil.FormatDay1), vv});
    }
}
