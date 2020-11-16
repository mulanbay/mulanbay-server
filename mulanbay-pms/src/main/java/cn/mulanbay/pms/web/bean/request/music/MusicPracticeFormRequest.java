package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.common.aop.BindUser;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class MusicPracticeFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotNull(message = "{validate.musicPractice.musicInstrumentId.NotNull}")
    private Long musicInstrumentId;

    @NotNull(message = "{validate.musicPractice.minutes.NotNull}")
    private Integer minutes;

    @NotNull(message = "{validate.musicPractice.practiceStartTime.NotNull}")
    private Date practiceStartTime;

    @NotNull(message = "{validate.musicPractice.practiceEndTime.NotNull}")
    private Date practiceEndTime;

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

    public Long getMusicInstrumentId() {
        return musicInstrumentId;
    }

    public void setMusicInstrumentId(Long musicInstrumentId) {
        this.musicInstrumentId = musicInstrumentId;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Date getPracticeStartTime() {
        return practiceStartTime;
    }

    public void setPracticeStartTime(Date practiceStartTime) {
        this.practiceStartTime = practiceStartTime;
    }

    public Date getPracticeEndTime() {
        return practiceEndTime;
    }

    public void setPracticeEndTime(Date practiceEndTime) {
        this.practiceEndTime = practiceEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
