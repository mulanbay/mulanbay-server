package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 热力图
 *
 * @author: fenghong
 * @date: 2022-08-14 11:32
 */
public class ChartHeatmapData extends BaseChartData{

    private double minValue = Double.MAX_VALUE;

    private double maxValue = Double.MIN_VALUE; ;

    private List<String> xdata = new ArrayList<>();

    private List<String> ydata = new ArrayList<>();

    private List<ChartHeatmapSerieData> series = new ArrayList<>();

    public void addXData(String v){
        this.xdata.add(v);
    }

    public void addYData(String v){
        this.ydata.add(v);
    }

    public void addSerieData(ChartHeatmapSerieData data){
        this.series.add(data);
    }

    /**
     * 更新最大最小值
     * @param v
     */
    public void updateMinMaxValue(double v){
        if(minValue>v){
            minValue =v;
        }
        if(maxValue<v){
            maxValue =v;
        }
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

    public List<String> getXdata() {
        return xdata;
    }

    public void setXdata(List<String> xdata) {
        this.xdata = xdata;
    }

    public List<String> getYdata() {
        return ydata;
    }

    public void setYdata(List<String> ydata) {
        this.ydata = ydata;
    }

    public List<ChartHeatmapSerieData> getSeries() {
        return series;
    }

    public void setSeries(List<ChartHeatmapSerieData> series) {
        this.series = series;
    }
}
