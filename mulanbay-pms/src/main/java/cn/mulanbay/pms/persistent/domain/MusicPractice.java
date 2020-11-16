package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 音乐练习记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "music_practice")
public class MusicPractice implements java.io.Serializable {
    private static final long serialVersionUID = 8552141637927335786L;
    private Long id;
    private Long userId;
    private MusicInstrument musicInstrument;
    private Integer minutes;
    private Date practiceDate;
    private Date practiceStartTime;
    private Date practiceEndTime;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "music_instrument_id")
    public MusicInstrument getMusicInstrument() {
        return musicInstrument;
    }

    public void setMusicInstrument(MusicInstrument musicInstrument) {
        this.musicInstrument = musicInstrument;
    }

    @Basic
    @Column(name = "minutes")
    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "practice_date", length = 10)
    public Date getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(Date practiceDate) {
        this.practiceDate = practiceDate;
    }

    @Basic
    @Column(name = "practice_start_time")
    public Date getPracticeStartTime() {
        return practiceStartTime;
    }

    public void setPracticeStartTime(Date practiceStartTime) {
        this.practiceStartTime = practiceStartTime;
    }

    @Basic
    @Column(name = "practice_end_time")
    public Date getPracticeEndTime() {
        return practiceEndTime;
    }

    public void setPracticeEndTime(Date practiceEndTime) {
        this.practiceEndTime = practiceEndTime;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
