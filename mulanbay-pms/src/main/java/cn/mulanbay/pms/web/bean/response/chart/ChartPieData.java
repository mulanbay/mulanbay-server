package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的饼状图数据
 * 只支持单个数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartPieData extends BaseChartData {

    private List<String> xdata = new ArrayList<>();

    private List<ChartPieSerieData> detailData = new ArrayList<>();

    public List<String> getXdata() {
        return xdata;
    }

    public void setXdata(List<String> xdata) {
        this.xdata = xdata;
    }

    public List<ChartPieSerieData> getDetailData() {
        return detailData;
    }

    public void setDetailData(List<ChartPieSerieData> detailData) {
        this.detailData = detailData;
    }

}
