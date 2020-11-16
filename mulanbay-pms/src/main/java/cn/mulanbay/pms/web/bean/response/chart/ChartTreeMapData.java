package cn.mulanbay.pms.web.bean.response.chart;

import java.util.List;

/**
 * echarts的矩形树形结构
 *
 * @see https://echarts.baidu.com/examples/editor.html?c=treemap-disk
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartTreeMapData extends BaseChartData {

    private String unit;

    private String name;

    List<ChartTreeMapDetailData> data;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChartTreeMapDetailData> getData() {
        return data;
    }

    public void setData(List<ChartTreeMapDetailData> data) {
        this.data = data;
    }
}
