package cn.mulanbay.pms.web.bean.response.chart;

/**
 * 词云图
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/14
 */
public class ChartWorldCloudDetailData {

    private double value;

    private String name;

    public ChartWorldCloudDetailData() {
    }

    public ChartWorldCloudDetailData(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
