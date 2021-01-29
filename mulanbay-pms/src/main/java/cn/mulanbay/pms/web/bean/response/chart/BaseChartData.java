package cn.mulanbay.pms.web.bean.response.chart;

/**
 * 图表数据返回基类
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class BaseChartData {

    //标题
    private String title;

    //子标题
    private String subTitle;

    //单位
    private String unit;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
