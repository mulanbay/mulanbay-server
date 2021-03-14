package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 关系图
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/14
 */
public class ChartGraphData {

    private double value;

    private String name;

    private long pid;

    public ChartGraphData() {
    }

    public ChartGraphData(double value, String name) {
        this.value = value;
        this.name = name;
    }

    private List<ChartGraphData> children = new ArrayList<>();

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

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public List<ChartGraphData> getChildren() {
        return children;
    }

    public void setChildren(List<ChartGraphData> children) {
        this.children = children;
    }
}
