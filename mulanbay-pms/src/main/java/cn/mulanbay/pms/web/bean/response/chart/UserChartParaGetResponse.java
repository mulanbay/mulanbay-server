package cn.mulanbay.pms.web.bean.response.chart;

import cn.mulanbay.pms.persistent.enums.ChartType;

/**
 * 图表数据查询参数返回数据
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class UserChartParaGetResponse {

    private String title;

    private String url;

    private String detailUrl;

    private String para;

    private String bindValues;

    private ChartType chartType;

    private Boolean supportDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public Boolean getSupportDate() {
        return supportDate;
    }

    public void setSupportDate(Boolean supportDate) {
        this.supportDate = supportDate;
    }
}
