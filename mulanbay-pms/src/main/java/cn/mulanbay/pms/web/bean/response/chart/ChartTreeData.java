package cn.mulanbay.pms.web.bean.response.chart;

/**
 * echarts的树形结构
 *
 * @see https://echarts.baidu.com/examples/editor.html?c=tree-basic
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartTreeData extends BaseChartData {

    private String unit;

    private String name;

    private ChartTreeDetailData data;

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

    public ChartTreeDetailData getData() {
        return data;
    }

    public void setData(ChartTreeDetailData data) {
        this.data = data;
    }
}
