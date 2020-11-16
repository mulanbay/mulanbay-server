package cn.mulanbay.pms.web.bean.response.chart;

import cn.mulanbay.common.util.DateUtil;

import java.util.*;

/**
 * 日历图数据（使用与热点图）
 * 多个年份的统计
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartCalendarMultiData extends BaseChartData {

    private Map<Integer, List<Object[]>> chartData = new TreeMap<>();

    //最大值、最小值用于界面计算每个点的大小使用，目前没有使用
    private double minValue;

    private double maxValue;

    private String unit;

    /**
     * @param year
     * @param datString 格式：2017-01-01
     * @param value
     */
    public void addData(int year, String datString, Object value) {
        this.addData(year, new Object[]{datString, value});
    }

    public void addData(int year, Object[] data) {
        List<Object[]> list = chartData.get(year);
        if (list == null) {
            list = new ArrayList<>();
            chartData.put(year, list);
        }
        Object[] os = null;
        double vv = Double.valueOf(data[1].toString());
        for (Object[] dd : list) {
            //如果已经包含，则累计
            if (dd[0].toString().equals(data[0].toString())) {
                dd[1] = vv + Double.valueOf(dd[1].toString());
                os = dd;
            }
        }
        if (os == null) {
            list.add(data);
        }
        // 计算最小最大值
        if (vv > maxValue) {
            maxValue = vv;
        }
        if (vv < minValue || minValue == 0) {
            //第一次或者比其小
            minValue = vv;
        }
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
    public void addData(int year, String dateString, double value, boolean dateRegion, int days) {
        if (!dateRegion) {
            this.addData(year, new Object[]{dateString, value});
        } else {
            Date startDate = DateUtil.getDate(dateString, DateUtil.FormatDay1);
            for (int i = 0; i < days; i++) {
                String newDateString = DateUtil.getFormatDate(DateUtil.getDate(i, startDate), DateUtil.FormatDay1);
                //平均下去了，所以每个只是1
                this.addData(year, new Object[]{newDateString, 1});
            }
        }
    }

    public List<Integer> getYears() {
        Set<Integer> keySet = chartData.keySet();
        List<Integer> years = new ArrayList<>();
        for (Integer key : keySet) {
            years.add(key);
        }
        return years;
    }

    public List<List<Object[]>> getSeries() {
        List<List<Object[]>> series = new ArrayList<>();
        Set<Integer> keySet = chartData.keySet();
        for (Integer key : keySet) {
            series.add(chartData.get(key));
        }
        return series;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
