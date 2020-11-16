package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.persistent.enums.MusicPracticeTuneLevel;

import java.util.Date;

public class MusicPracticeTuneLevelStat {

    private String tune;

    private Date minPracticeDate;

    private Date maxPracticeDate;

    private MusicPracticeTuneLevel level;

    public Date getMinPracticeDate() {
        return minPracticeDate;
    }

    public void setMinPracticeDate(Date minPracticeDate) {
        this.minPracticeDate = minPracticeDate;
    }

    public Date getMaxPracticeDate() {
        return maxPracticeDate;
    }

    public void setMaxPracticeDate(Date maxPracticeDate) {
        this.maxPracticeDate = maxPracticeDate;
    }

    public String getTune() {
        return tune;
    }

    public void setTune(String tune) {
        this.tune = tune;
    }

    public MusicPracticeTuneLevel getLevel() {
        return level;
    }

    public void setLevel(MusicPracticeTuneLevel level) {
        this.level = level;
    }

    public String getLevelName() {
        return level == null ? null : level.getName();
    }

    public Integer getLevelIndex() {
        return level == null ? null : level.ordinal();
    }

}
