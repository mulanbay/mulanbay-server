package cn.mulanbay.pms.web.bean.response.user;

public class UserGeneralLifeStatVo {

    // 锻炼总小时数
    private Double totalSportExerciseHours = 0.0;

    // 锻炼次数
    private Long totalSportExerciseCount = 0L;

    // 阅读总小时数
    private Double totalReadingHours = 0.0;

    // 阅读总次数
    private Long totalReadingCount = 0L;

    // 音乐练习总小时数
    private Double totalMusicPracticeHours = 0.0;

    // 音乐练习总次数
    private Long totalMusicPracticeCount = 0L;

    public Double getTotalSportExerciseHours() {
        return totalSportExerciseHours;
    }

    public void setTotalSportExerciseHours(Double totalSportExerciseHours) {
        this.totalSportExerciseHours = totalSportExerciseHours;
    }

    public Long getTotalSportExerciseCount() {
        return totalSportExerciseCount;
    }

    public void setTotalSportExerciseCount(Long totalSportExerciseCount) {
        this.totalSportExerciseCount = totalSportExerciseCount;
    }

    public Double getTotalReadingHours() {
        return totalReadingHours;
    }

    public void setTotalReadingHours(Double totalReadingHours) {
        this.totalReadingHours = totalReadingHours;
    }

    public Long getTotalReadingCount() {
        return totalReadingCount;
    }

    public void setTotalReadingCount(Long totalReadingCount) {
        this.totalReadingCount = totalReadingCount;
    }

    public Double getTotalMusicPracticeHours() {
        return totalMusicPracticeHours;
    }

    public void setTotalMusicPracticeHours(Double totalMusicPracticeHours) {
        this.totalMusicPracticeHours = totalMusicPracticeHours;
    }

    public Long getTotalMusicPracticeCount() {
        return totalMusicPracticeCount;
    }

    public void setTotalMusicPracticeCount(Long totalMusicPracticeCount) {
        this.totalMusicPracticeCount = totalMusicPracticeCount;
    }
}
