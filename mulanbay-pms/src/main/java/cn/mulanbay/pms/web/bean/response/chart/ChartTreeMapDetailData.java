package cn.mulanbay.pms.web.bean.response.chart;

import cn.mulanbay.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * echarts的矩形树形结构
 *
 * @see https://echarts.baidu.com/examples/editor.html?c=treemap-disk
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartTreeMapDetailData {

    private double value;

    private String name;

    private String path;

    public ChartTreeMapDetailData() {
    }

    public ChartTreeMapDetailData(double value, String name, String path) {
        this.value = value;
        this.name = name;
        this.path = path;
    }

    private List<ChartTreeMapDetailData> children;

    /**
     * 添加子节点
     *
     * @param value
     * @param name
     * @param path
     */
    public void addChild(double value, String name, String path) {
        this.addChild(new ChartTreeMapDetailData(value, name, path));
    }

    public void addChild(ChartTreeMapDetailData tmb) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(tmb);
    }

    /**
     * 添加子节点
     *
     * @param value
     * @param name
     * @param path
     */
    public void findAndAppendChild(double value, String name, String path) {
        ChartTreeMapDetailData ct = this.findChild(name);
        if (ct == null) {
            this.addChild(new ChartTreeMapDetailData(value, name, path));
        } else {
            ct.setValue(ct.getValue() + value);
        }
    }

    private ChartTreeMapDetailData findChild(String name) {
        if (children == null) {
            return null;
        }
        for (ChartTreeMapDetailData ct : children) {
            if (ct.getName().equals(name)) {
                return ct;
            }
        }
        return null;
    }

    /**
     * 如果有子节点，则由子节点的总和决定
     *
     * @return
     */
    public double getValue() {
        if (StringUtil.isNotEmpty(children)) {
            double b = 0;
            for (ChartTreeMapDetailData ct : children) {
                b += ct.getValue();
            }
            return b;
        }
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ChartTreeMapDetailData> getChildren() {
        return children;
    }

    public void setChildren(List<ChartTreeMapDetailData> children) {
        this.children = children;
    }
}
