package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 极坐标图表的数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartPolarBarData extends BaseChartData {

    private List<String> legendData = new ArrayList<>();

    //x轴数据
    private List<String> xdata = new ArrayList<>();

    //y轴数据ChartData
    private List<ChartYData> ydata = new ArrayList<>();

    /**
     * 获取索引
     *
     * @param s
     * @return
     */
    public int getLegendIndex(String s) {
        for (int i = 0; i < legendData.size(); i++) {
            if (legendData.get(i).equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public List<String> getLegendData() {
        return legendData;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public List<String> getXdata() {
        return xdata;
    }

    public void setXdata(List<String> xdata) {
        this.xdata = xdata;
    }

    public List<ChartYData> getYdata() {
        return ydata;
    }

    public void setYdata(List<ChartYData> ydata) {
        this.ydata = ydata;
    }
}
