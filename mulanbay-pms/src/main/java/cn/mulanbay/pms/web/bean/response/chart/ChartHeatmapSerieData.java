package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 雷达图的数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartHeatmapSerieData {

    private String name;

    /**
     * 数据值
     * index0: x轴索引编号
     * index1：y轴索引编号
     * index2：具体的值
     * index3：单位
     * 其他自定义
     */
    private List<Object[]> data = new ArrayList<>();

    public void addData(Object[] vv){
        this.data.add(vv);
    }

    public ChartHeatmapSerieData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }
}
