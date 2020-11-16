package cn.mulanbay.pms.web.bean.request.read;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReadingRecordDetailFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotNull(message = "{validate.readingRecordDetail.readingRecordId.NotNull}")
    private Long readingRecordId;
    //阅读日期
    @NotNull(message = "{validate.readingRecordDetail.readTime.NotNull}")
    private Date readTime;

    @NotNull(message = "{validate.readingRecordDetail.minutes.NotNull}")
    private Integer minutes;

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

    public Long getReadingRecordId() {
        return readingRecordId;
    }

    public void setReadingRecordId(Long readingRecordId) {
        this.readingRecordId = readingRecordId;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
