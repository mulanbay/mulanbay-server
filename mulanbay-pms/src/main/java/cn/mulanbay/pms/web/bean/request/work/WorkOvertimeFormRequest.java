package cn.mulanbay.pms.web.bean.request.work;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class WorkOvertimeFormRequest implements BindUser {

    private Long id;

    private Long userId;

    @NotNull(message = "{validate.workOvertime.companyId.NotNull}")
    private Long companyId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    //@NotNull(message = "{validate.workOvertime.workDate.NotNull}")
    private Date workDate;

    @NotNull(message = "{validate.workOvertime.workStartTime.NotNull}")
    private Date workStartTime;

    @NotNull(message = "{validate.workOvertime.workEndTime.NotNull}")
    private Date workEndTime;

    //@NotNull(message = "{validate.workOvertime.hours.NotNull}")
    private Double hours;

    private String remark;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
