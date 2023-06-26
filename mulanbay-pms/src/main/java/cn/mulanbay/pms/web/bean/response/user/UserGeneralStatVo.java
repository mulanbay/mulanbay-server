package cn.mulanbay.pms.web.bean.response.user;

public class UserGeneralStatVo {

    // 本月预算
    private Double monthBudget = 0.0;

    // 本月预测消费
    private Double monthPredict;

    //本年预算
    private Double yearBudget = 0.0;

    // 本年预测消费
    private Double yearPredict;

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

    public Double getMonthPredict() {
        return monthPredict;
    }

    public void setMonthPredict(Double monthPredict) {
        this.monthPredict = monthPredict;
    }

    public Double getYearPredict() {
        return yearPredict;
    }

    public void setYearPredict(Double yearPredict) {
        this.yearPredict = yearPredict;
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
