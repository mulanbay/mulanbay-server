package cn.mulanbay.pms.web.bean.request.chart;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.BindUserLevel;
import cn.mulanbay.pms.persistent.enums.CommonStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserChartFormRequest implements BindUser, BindUserLevel {

    private Long id;

    @NotEmpty(message = "{validate.userChart.title.notEmpty}")
    private String title;

    private Long userId;

    @NotNull(message = "{validate.userChart.chartConfigId.NotNull}")
    private Long chartConfigId;

    private String bindValues;

    @NotNull(message = "{validate.userChart.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.userChart.orderIndex.NotNull}")
    private Short orderIndex;

    @NotNull(message = "{validate.userChart.showInIndex.NotNull}")
    private Boolean showInIndex;

    private String remark;

    //绑定的用户等级
    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChartConfigId() {
        return chartConfigId;
    }

    public void setChartConfigId(Long chartConfigId) {
        this.chartConfigId = chartConfigId;
    }

    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
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

    public Boolean getShowInIndex() {
        return showInIndex;
    }

    public void setShowInIndex(Boolean showInIndex) {
        this.showInIndex = showInIndex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }
}
