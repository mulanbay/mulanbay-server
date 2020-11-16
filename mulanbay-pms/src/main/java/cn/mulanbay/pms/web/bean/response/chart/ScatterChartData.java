package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 散点图
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ScatterChartData extends BaseChartData {

    private String xUnit;

    private String yUnit;

    private String vUnit = "次";

    private double minValue = Double.MAX_VALUE;

    private double maxValue;

    private List<String> legendData = new ArrayList<>();

    private List<ScatterChartDetailData> seriesDatas = new ArrayList<>();

    public void addSeriesData(ScatterChartDetailData data) {
        seriesDatas.add(data);
    }

    public String getxUnit() {
        return xUnit;
    }

    public void setxUnit(String xUnit) {
        this.xUnit = xUnit;
    }

    public String getyUnit() {
        return yUnit;
    }

    public void setyUnit(String yUnit) {
        this.yUnit = yUnit;
    }

    public List<String> getLegendData() {
        return legendData;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public List<ScatterChartDetailData> getSeriesDatas() {
        return seriesDatas;
    }

    public void setSeriesDatas(List<ScatterChartDetailData> seriesDatas) {
        this.seriesDatas = seriesDatas;
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

    public String getvUnit() {
        return vUnit;
    }

    public void setvUnit(String vUnit) {
        this.vUnit = vUnit;
    }

    public void addLegent(String s) {
        legendData.add(s);
    }

    public ScatterChartDetailData findDetailDataByName(String name) {
        for (ScatterChartDetailData data : seriesDatas) {
            if (data.getName().equals(name)) {
                return data;
            }
        }
        return null;
    }

    public void valueCompare(double value) {
        if (value > maxValue) {
            maxValue = value;
        }
        if (value < minValue) {
            minValue = value;
        }
    }
}
