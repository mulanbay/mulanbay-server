package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.MusicPracticeTuneLevel;
import cn.mulanbay.pms.persistent.enums.MusicPracticeTuneType;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 音乐练习曲子
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "music_practice_tune")
public class MusicPracticeTune implements java.io.Serializable {
    private static final long serialVersionUID = 7184640780429015652L;
    private Long id;
    private Long userId;
    private MusicPractice musicPractice;
    private String tune;
    private Integer times;
    private MusicPracticeTuneLevel level;
    private MusicPracticeTuneType tuneType;
    private String remark;

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
    @JoinColumn(name = "music_practice_id")
    public MusicPractice getMusicPractice() {
        return musicPractice;
    }

    public void setMusicPractice(MusicPractice musicPractice) {
        this.musicPractice = musicPractice;
    }

    @Basic
    @Column(name = "tune")
    public String getTune() {
        return tune;
    }

    public void setTune(String tune) {
        this.tune = tune;
    }

    @Basic
    @Column(name = "times")
    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    @Basic
    @Column(name = "level")
    public MusicPracticeTuneLevel getLevel() {
        return level;
    }

    public void setLevel(MusicPracticeTuneLevel level) {
        this.level = level;
    }

    @Basic
    @Column(name = "tune_type")
    public MusicPracticeTuneType getTuneType() {
        return tuneType;
    }

    public void setTuneType(MusicPracticeTuneType tuneType) {
        this.tuneType = tuneType;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    public String getLevelName() {
        if (level != null) {
            return level.getName();
        } else {
            return null;
        }
    }

    @Transient
    public String getTuneTypeName() {
        if (tuneType != null) {
            return tuneType.getName();
        } else {
            return null;
        }
    }
}
