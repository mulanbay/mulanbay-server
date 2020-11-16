package cn.mulanbay.pms.persistent.dto;

public class MusicPracticeTuneStat {

    private String name;

    //次数
    private Long totalTimes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Long totalTimes) {
        this.totalTimes = totalTimes;
    }
}
