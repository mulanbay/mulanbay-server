package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.BindUser;

public class AccountForecastRequest implements BindUser {

    private Long userId;

    //当前账户余额
    private double currentAmount;

    //每月工资
    private double monthlySalary;

    //每月其他收入
    private double monthlyOtherIncome;

    //每月花费
    private double monthlyConsume;

    //年收益（年终奖之类）
    private double yearlyIncome;

    //每月工资增长率
    private double yearlySalaryRate;

    //存款投资比例
    private double amountInvestRate;

    //每年存款收益率
    private double yearlyAmountRate;

    //固定支出
    private double fixExpend;

    //0:预测距离目标余额 1：预测n个月后
    private int forecastType;

    private int forecastValue;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public double getMonthlyOtherIncome() {
        return monthlyOtherIncome;
    }

    public void setMonthlyOtherIncome(double monthlyOtherIncome) {
        this.monthlyOtherIncome = monthlyOtherIncome;
    }

    public double getMonthlyConsume() {
        return monthlyConsume;
    }

    public void setMonthlyConsume(double monthlyConsume) {
        this.monthlyConsume = monthlyConsume;
    }

    public double getYearlyIncome() {
        return yearlyIncome;
    }

    public void setYearlyIncome(double yearlyIncome) {
        this.yearlyIncome = yearlyIncome;
    }

    public double getYearlySalaryRate() {
        return yearlySalaryRate;
    }

    public void setYearlySalaryRate(double yearlySalaryRate) {
        this.yearlySalaryRate = yearlySalaryRate;
    }

    public double getAmountInvestRate() {
        return amountInvestRate;
    }

    public void setAmountInvestRate(double amountInvestRate) {
        this.amountInvestRate = amountInvestRate;
    }

    public double getYearlyAmountRate() {
        return yearlyAmountRate;
    }

    public void setYearlyAmountRate(double yearlyAmountRate) {
        this.yearlyAmountRate = yearlyAmountRate;
    }

    public double getFixExpend() {
        return fixExpend;
    }

    public void setFixExpend(double fixExpend) {
        this.fixExpend = fixExpend;
    }

    public int getForecastType() {
        return forecastType;
    }

    public void setForecastType(int forecastType) {
        this.forecastType = forecastType;
    }

    public int getForecastValue() {
        return forecastValue;
    }

    public void setForecastValue(int forecastValue) {
        this.forecastValue = forecastValue;
    }
}
