package cn.mulanbay.pms.web.bean.response.fund;

import cn.mulanbay.pms.persistent.domain.Budget;

import java.util.Date;

public class BudgetDetailVo extends Budget {

    //下一次支付时间
    private Date nextPaytime;

    //跟实际需要实现的还剩余几天
    private Integer leftDays;

    //本期支付时间
    private Date cpPaidTime;

    private Double cpPaidAmount;

    public Date getNextPaytime() {
        return nextPaytime;
    }

    public void setNextPaytime(Date nextPaytime) {
        this.nextPaytime = nextPaytime;
    }

    public Integer getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(Integer leftDays) {
        this.leftDays = leftDays;
    }

    public Date getCpPaidTime() {
        return cpPaidTime;
    }

    public void setCpPaidTime(Date cpPaidTime) {
        this.cpPaidTime = cpPaidTime;
    }

    public Double getCpPaidAmount() {
        return cpPaidAmount;
    }

    public void setCpPaidAmount(Double cpPaidAmount) {
        this.cpPaidAmount = cpPaidAmount;
    }
}
