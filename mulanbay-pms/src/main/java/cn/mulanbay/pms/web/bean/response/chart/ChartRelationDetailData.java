package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 关系图
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/14
 */
public class ChartRelationDetailData {

    private double value;

    private String name;

    public ChartRelationDetailData() {
    }

    public ChartRelationDetailData(double value, String name) {
        this.value = value;
        this.name = name;
    }

    private List<ChartRelationDetailData> children = new ArrayList<>();

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

    public List<ChartRelationDetailData> getChildren() {
        return children;
    }

    public void setChildren(List<ChartRelationDetailData> children) {
        this.children = children;
    }
}
