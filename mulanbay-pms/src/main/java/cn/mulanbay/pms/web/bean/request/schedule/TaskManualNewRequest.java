package cn.mulanbay.pms.web.bean.request.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TaskManualNewRequest {

    private Long taskTriggerId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bussDate;

    private boolean sync;

    public Long getTaskTriggerId() {
        return taskTriggerId;
    }

    public void setTaskTriggerId(Long taskTriggerId) {
        this.taskTriggerId = taskTriggerId;
    }

    public Date getBussDate() {
        return bussDate;
    }

    public void setBussDate(Date bussDate) {
        this.bussDate = bussDate;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
