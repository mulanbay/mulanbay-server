package cn.mulanbay.pms.web.bean.response.fund;

import java.util.ArrayList;
import java.util.List;

public class BudgetAnalyseVo {

    private String title;

    //预算金额
    private double budgetAmount;

    //最近一次收入(月收入或年收入)
    private double lastIncome;

    //实际普通消费金额
    private double ncAmount;

    //实际突发消费金额
    private double bcAmount;

    //实际看病消费金额
    private double trAmount;

    private List<BudgetInfoVo> budgetList = new ArrayList<>();

    /**
     * 添加
     *
     * @param bib
     */
    public void addBudget(BudgetInfoVo bib) {
        budgetList.add(bib);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public double getLastIncome() {
        return lastIncome;
    }

    public void setLastIncome(double lastIncome) {
        this.lastIncome = lastIncome;
    }

    public double getNcAmount() {
        return ncAmount;
    }

    public void setNcAmount(double ncAmount) {
        this.ncAmount = ncAmount;
    }

    public double getBcAmount() {
        return bcAmount;
    }

    public void setBcAmount(double bcAmount) {
        this.bcAmount = bcAmount;
    }

    public double getTrAmount() {
        return trAmount;
    }

    public void setTrAmount(double trAmount) {
        this.trAmount = trAmount;
    }

    public List<BudgetInfoVo> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<BudgetInfoVo> budgetList) {
        this.budgetList = budgetList;
    }

    /**
     * 总的消费金额
     *
     * @return
     */
    public double getConsumeAmount() {
        return ncAmount + bcAmount + trAmount;
    }
}
