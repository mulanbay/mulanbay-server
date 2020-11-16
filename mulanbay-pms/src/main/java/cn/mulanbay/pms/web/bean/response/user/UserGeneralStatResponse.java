package cn.mulanbay.pms.web.bean.response.user;

public class UserGeneralStatResponse {

    // 本月预算
    private Double monthBudget = 0.0;

    //本年预算
    private Double yearBudget = 0.0;

    // 预算
    private Double totalIncome = 0.0;

    // 消费总额，当前查询条件下(包括看病的金额)
    private Double totalConsumeAmount = 0.0;

    // 消费次数
    private Long totalBuyCount = 0L;

    // 看病总额
    private Double totalTreatAmount = 0.0;

    // 看病次数
    private Long totalTreatCount = 0L;

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

    // 本月已过去时间（百分比）
    private double dayMonthRate = 0;

    //这个月剩余天数
    private int remainMonthDays;

    //这个月的消费总额
    private double monthConsumeAmount;

    //这个月总天数
    private int monthDays;

    //这个月已经过去天数
    private int monthPassDays;

    public Double getMonthBudget() {
        return monthBudget;
    }

    public void setMonthBudget(Double monthBudget) {
        this.monthBudget = monthBudget;
    }

    public Double getYearBudget() {
        return yearBudget;
    }

    public void setYearBudget(Double yearBudget) {
        this.yearBudget = yearBudget;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalConsumeAmount() {
        return totalConsumeAmount;
    }

    public void setTotalConsumeAmount(Double totalConsumeAmount) {
        this.totalConsumeAmount = totalConsumeAmount;
    }

    public Long getTotalBuyCount() {
        return totalBuyCount;
    }

    public void setTotalBuyCount(Long totalBuyCount) {
        this.totalBuyCount = totalBuyCount;
    }

    public Double getTotalTreatAmount() {
        return totalTreatAmount;
    }

    public void setTotalTreatAmount(Double totalTreatAmount) {
        this.totalTreatAmount = totalTreatAmount;
    }

    public Long getTotalTreatCount() {
        return totalTreatCount;
    }

    public void setTotalTreatCount(Long totalTreatCount) {
        this.totalTreatCount = totalTreatCount;
    }

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

    public double getDayMonthRate() {
        return dayMonthRate;
    }

    public void setDayMonthRate(double dayMonthRate) {
        this.dayMonthRate = dayMonthRate;
    }

    public int getRemainMonthDays() {
        return remainMonthDays;
    }

    public void setRemainMonthDays(int remainMonthDays) {
        this.remainMonthDays = remainMonthDays;
    }

    public double getMonthConsumeAmount() {
        return monthConsumeAmount;
    }

    public void setMonthConsumeAmount(double monthConsumeAmount) {
        this.monthConsumeAmount = monthConsumeAmount;
    }

    public int getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(int monthDays) {
        this.monthDays = monthDays;
    }

    public int getMonthPassDays() {
        return monthPassDays;
    }

    public void setMonthPassDays(int monthPassDays) {
        this.monthPassDays = monthPassDays;
    }

    /**
     * 添加消费
     *
     * @param v
     */
    public void appendConsume(double v) {
        totalConsumeAmount += v;
    }

    /**
     * 添加消费
     *
     * @param v
     */
    public void appendMonthConsume(double v) {
        monthConsumeAmount += v;
    }
}
