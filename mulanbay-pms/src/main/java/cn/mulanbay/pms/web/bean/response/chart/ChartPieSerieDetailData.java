package cn.mulanbay.pms.web.bean.response.chart;

/**
 * 饼图的数据明细
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class ChartPieSerieDetailData {

    private String name;

    private Object value;

    public ChartPieSerieDetailData() {
    }

    public ChartPieSerieDetailData(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
