package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * y轴数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartYData {

    private String name;

    //堆叠的分组，空为不需要
    private String stack;

    //混合柱状图、折线图模式下使用
    private String unit;

    private List<Object> data = new ArrayList<>();

    public ChartYData() {
    }

    public ChartYData(String name) {
        this.name = name;
    }

    public ChartYData(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
