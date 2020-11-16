package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆叠条形图
 *
 * @see https://echarts.baidu.com/examples/editor.html?c=bar-y-category-stack
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartShadowData extends BaseChartData {

    private List<String> legendData = new ArrayList<>();

    private List<String> yaxisData = new ArrayList<>();

    private List<ChartShadowSerieData> series = new ArrayList<>();

    public void addLegend(String s) {
        legendData.add(s);
    }

    public void addYaxis(String s) {
        yaxisData.add(s);
    }

    public void addSerie(ChartShadowSerieData serie) {
        series.add(serie);
    }

    public List<String> getLegendData() {
        return legendData;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public List<String> getYaxisData() {
        return yaxisData;
    }

    public void setYaxisData(List<String> yaxisData) {
        this.yaxisData = yaxisData;
    }

    public List<ChartShadowSerieData> getSeries() {
        return series;
    }

    public void setSeries(List<ChartShadowSerieData> series) {
        this.series = series;
    }
}
