package cn.mulanbay.pms.handler.bean;

import cn.mulanbay.pms.persistent.domain.Budget;

import java.util.ArrayList;
import java.util.List;

/**
 * 预算封装类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BudgetAmountBean {

    //月度预算
    private double monthBudget = 0;

    //月度预算列表(可能包括单次、周之类)
    private List<Budget> monthBudgetList = new ArrayList<>();

    //年度预算
    private double yearBudget = 0;

    //月度预算列表(可能包括单次、周之类)
    private List<Budget> yearBudgetList = new ArrayList<>();

    /**
     * 增加月度预算
     *
     * @param v
     */
    public void addMonthBudget(double v) {
        monthBudget += v;
    }

    /**
     * 增加月度预算
     *
     * @param budget
     */
    public void addMonthBudget(Budget budget) {
        monthBudgetList.add(budget);
    }

    /**
     * 增加年度预算
     *
     * @param v
     */
    public void addYearBudget(double v) {
        yearBudget += v;
    }

    /**
     * 增加年度预算
     *
     * @param budget
     */
    public void addYearBudget(Budget budget) {
        yearBudgetList.add(budget);
    }

    public double getMonthBudget() {
        return monthBudget;
    }

    public void setMonthBudget(double monthBudget) {
        this.monthBudget = monthBudget;
    }

    public double getYearBudget() {
        return yearBudget;
    }

    public void setYearBudget(double yearBudget) {
        this.yearBudget = yearBudget;
    }

    public List<Budget> getMonthBudgetList() {
        return monthBudgetList;
    }

    public List<Budget> getYearBudgetList() {
        return yearBudgetList;
    }
}
