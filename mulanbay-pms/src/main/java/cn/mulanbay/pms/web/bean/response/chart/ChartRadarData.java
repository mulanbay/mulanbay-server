package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 雷达图的数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartRadarData extends BaseChartData {

    private List<String> legendData = new ArrayList<>();

    private List<ChartRadarIndicatorData> indicatorData = new ArrayList<>();

    private List<ChartRadarSerieData> series = new ArrayList<>();

    public List<String> getLegendData() {
        return legendData;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public void addLegend(String s){
        this.legendData.add(s);
    }

    public void addSerie(ChartRadarSerieData serieData){
        this.series.add(serieData);
    }

    /**
     * 增加
     *
     * @param text
     * @param max
     */
    public void addIndicatorData(String text, long max) {
        ChartRadarIndicatorData cr = new ChartRadarIndicatorData();
        cr.setText(text);
        cr.setMax(max);
        indicatorData.add(cr);
    }

    public List<ChartRadarIndicatorData> getIndicatorData() {
        return indicatorData;
    }

    public void setIndicatorData(List<ChartRadarIndicatorData> indicatorData) {
        this.indicatorData = indicatorData;
    }

    public List<ChartRadarSerieData> getSeries() {
        return series;
    }

    public void setSeries(List<ChartRadarSerieData> series) {
        this.series = series;
    }
}
