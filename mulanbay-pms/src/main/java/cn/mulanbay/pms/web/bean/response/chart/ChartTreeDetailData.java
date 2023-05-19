package cn.mulanbay.pms.web.bean.response.chart;

import cn.mulanbay.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * echarts的树形结构
 *
 * @see https://echarts.baidu.com/examples/editor.html?c=tree-basic
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartTreeDetailData {

    private double value;

    private String name;

    /**
     * 是否统计子类的值
     */
    private boolean sumChildrenValue = true;

    public ChartTreeDetailData() {
    }

    public ChartTreeDetailData(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public ChartTreeDetailData(double value, String name, boolean sumChildrenValue) {
        this.value = value;
        this.name = name;
        this.sumChildrenValue = sumChildrenValue;
    }

    private List<ChartTreeDetailData> children = new ArrayList<>();

    /**
     * 添加子节点
     *
     * @param value
     * @param name
     */
    public void addChild(double value, String name) {
        this.addChild(new ChartTreeDetailData(value, name));
    }

    /**
     * 添加子节点
     *
     * @param value
     * @param name
     */
    public void addChild(double value, String name, boolean sumChildrenValue) {
        this.addChild(new ChartTreeDetailData(value, name,sumChildrenValue));
    }

    public void addChild(ChartTreeDetailData tmb) {
        children.add(tmb);
    }

    /**
     * 添加子节点
     *
     * @param value
     * @param name
     */
    public void findAndAppendChild(double value, String name) {
        ChartTreeDetailData ct = this.findChild(name);
        if (ct == null) {
            this.addChild(new ChartTreeDetailData(value, name));
        } else {
            ct.setValue(ct.getValue() + value);
        }
    }

    public ChartTreeDetailData findChild(String name) {
        if (children == null) {
            return null;
        }
        for (ChartTreeDetailData ct : children) {
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
        if(!sumChildrenValue){
            return value;
        }
        if (StringUtil.isNotEmpty(children)) {
            double b = 0;
            for (ChartTreeDetailData ct : children) {
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

    public List<ChartTreeDetailData> getChildren() {
        return children;
    }

    public void setChildren(List<ChartTreeDetailData> children) {
        this.children = children;
    }
}
