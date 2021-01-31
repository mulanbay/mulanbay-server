package cn.mulanbay.pms.web.bean.request.log;

import cn.mulanbay.pms.persistent.enums.LogLevel;
import cn.mulanbay.pms.persistent.enums.MonitorBussType;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ErrorCodeDefineFormRequest {

    @NotNull(message = "{validate.errorCodeDefine.code.NotNull}")
    private Integer code;

    @NotEmpty(message = "{validate.errorCodeDefine.name.notEmpty}")
    private String name;

    @NotNull(message = "{validate.errorCodeDefine.level.NotNull}")
    private LogLevel level;

    @NotNull(message = "{validate.errorCodeDefine.notifiable.NotNull}")
    private Boolean notifiable;

    @NotNull(message = "{validate.errorCodeDefine.realtimeNotify.NotNull}")
    private Boolean realtimeNotify;

    @NotNull(message = "{validate.errorCodeDefine.loggable.NotNull}")
    private Boolean loggable;

    @NotEmpty(message = "{validate.errorCodeDefine.causes.notEmpty}")
    private String causes;

    private String solutions;

    @NotNull(message = "{validate.errorCodeDefine.bussType.NotNull}")
    private MonitorBussType bussType;

    private Integer limitPeriod;

    /**
     * pc端连接
     */
    private String url;
    /**
     * 移动端连接
     */
    private String mobileUrl;

    private String remark;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public Boolean getNotifiable() {
        return notifiable;
    }

    public void setNotifiable(Boolean notifiable) {
        this.notifiable = notifiable;
    }

    public Boolean getRealtimeNotify() {
        return realtimeNotify;
    }

    public void setRealtimeNotify(Boolean realtimeNotify) {
        this.realtimeNotify = realtimeNotify;
    }

    public Boolean getLoggable() {
        return loggable;
    }

    public void setLoggable(Boolean loggable) {
        this.loggable = loggable;
    }

    public String getCauses() {
        return causes;
    }

    public void setCauses(String causes) {
        this.causes = causes;
    }

    public String getSolutions() {
        return solutions;
    }

    public void setSolutions(String solutions) {
        this.solutions = solutions;
    }

    public MonitorBussType getBussType() {
        return bussType;
    }

    public void setBussType(MonitorBussType bussType) {
        this.bussType = bussType;
    }

    public Integer getLimitPeriod() {
        return limitPeriod;
    }

    public void setLimitPeriod(Integer limitPeriod) {
        this.limitPeriod = limitPeriod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
