package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆叠条形图
 *
 * @see https://echarts.baidu.com/examples/editor.html?c=bar-y-category-stack
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartShadowSerieData {

    private String name;

    private String stack;

    private List<String> data = new ArrayList<>();

    public ChartShadowSerieData(String name, String stack) {
        this.name = name;
        this.stack = stack;
    }

    public void addData(String s) {
        data.add(s);
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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
