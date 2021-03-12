package cn.mulanbay.pms.web.bean.request.chart;

import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChartConfigFormRequest {

    private Long id;

    @NotEmpty(message = "{validate.chartConfig.name.NotEmpty}")
    private String name;

    @NotEmpty(message = "{validate.chartConfig.title.NotEmpty}")
    private String title;
    //请求参数
    @NotEmpty(message = "{validate.chartConfig.para.NotEmpty}")
    private String para;

    @NotNull(message = "{validate.chartConfig.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.chartConfig.orderIndex.NotNull}")
    private Short orderIndex;

    //关联的bean，可以根据这个在各个其他界面来查询本业务管理的计划
    @NotEmpty(message = "{validate.chartConfig.relatedBeans.NotEmpty}")
    private String relatedBeans;

    @NotNull(message = "{validate.chartConfig.level.NotNull}")
    private Integer level;
    //链接地址
    @NotEmpty(message = "{validate.chartConfig.url.NotEmpty}")
    private String url;

    private String detailUrl;

    @NotNull(message = "{validate.chartConfig.chartType.NotNull}")
    private ChartType chartType;

    @NotNull(message = "{validate.chartConfig.supportDate.NotNull}")
    private Boolean supportDate;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Short getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Short orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
