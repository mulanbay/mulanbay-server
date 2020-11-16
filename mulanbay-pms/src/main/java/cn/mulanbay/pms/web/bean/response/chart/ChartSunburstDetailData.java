package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ChartSunburstDetailData {

    private String name;

    private List<ChartSunburstDetailData> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChartSunburstDetailData> getChildren() {
        return children;
    }

    public void setChildren(List<ChartSunburstDetailData> children) {
        this.children = children;
    }

    /**
     * 添加子类
     * @param childName
     */
    public void addChild(String childName){
        ChartSunburstDetailData child = new ChartSunburstDetailData();
        child.setName(childName);
        this.children.add(child);
    }

    /**
     * 添加子类
     * @param child
     */
    public void addChild(ChartSunburstDetailData child){
        this.children.add(child);
    }
}
