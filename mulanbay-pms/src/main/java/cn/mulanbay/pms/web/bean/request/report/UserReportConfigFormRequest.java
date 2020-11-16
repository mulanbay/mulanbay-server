package cn.mulanbay.pms.web.bean.request.report;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.BindUserLevel;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserReportConfigFormRequest implements BindUser, BindUserLevel {

    private Long id;
    private Long userId;

    @NotEmpty(message = "{validate.userReportConfig.title.notEmpty}")
    private String title;
    //用户自己选择的值
    private String bindValues;
    @NotNull(message = "{validate.userReportConfig.reportConfigId.NotNull}")
    private Long reportConfigId;

    @NotNull(message = "{validate.userReportConfig.status.NotNull}")
    private CommonStatus status;

    @NotNull(message = "{validate.userReportConfig.orderIndex.NotNull}")
    private Short orderIndex;

    @NotNull(message = "{validate.userReportConfig.remind.NotNull}")
    private Boolean remind;
    private String remark;

    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBindValues() {
        return bindValues;
    }

    public void setBindValues(String bindValues) {
        this.bindValues = bindValues;
    }

    public Long getReportConfigId() {
        return reportConfigId;
    }

    public void setReportConfigId(Long reportConfigId) {
        this.reportConfigId = reportConfigId;
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

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
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
