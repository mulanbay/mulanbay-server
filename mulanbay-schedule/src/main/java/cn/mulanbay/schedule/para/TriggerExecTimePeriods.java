package cn.mulanbay.schedule.para;

/**
 * 调度参数检查
 */
public class TriggerExecTimePeriods {

    /**
     * 允许允许的星期:1,2,3,4,5,6
     */
    private int[] weeks;

    /**
     * 允许运行的时间段08:00-13:00,14:00-23:00
     */
    private String times;

    public int[] getWeeks() {
        return weeks;
    }

    public void setWeeks(int[] weeks) {
        this.weeks = weeks;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
