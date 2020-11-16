package cn.mulanbay.pms.web.bean.request.sport;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SportExerciseCTFormRequest implements BindUser {

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date templateDate;

    private Date beginTime;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getTemplateDate() {
        return templateDate;
    }

    public void setTemplateDate(Date templateDate) {
        this.templateDate = templateDate;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
}
