package cn.mulanbay.pms.web.bean.response.fund;

import cn.mulanbay.pms.persistent.domain.Budget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetDetailVo extends Budget {

    //下一次支付时间
    private Date nextPaytime;

    //跟实际需要实现的还剩余几天
    private Integer leftDays;

    //本期支付时间
    private Date cpPaidTime;

    private Double cpPaidAmount;

    private String bussKey;

    List<BudgetDetailVo> children;

    /**
     * 增加子类
     * @param vo
     */
    public void addChild(BudgetDetailVo vo){
        if(children==null){
            children = new ArrayList<>();
        }
        children.add(vo);
    }

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

    public String getBussKey() {
        return bussKey;
    }

    public void setBussKey(String bussKey) {
        this.bussKey = bussKey;
    }

    public List<BudgetDetailVo> getChildren() {
        return children;
    }

    public void setChildren(List<BudgetDetailVo> children) {
        this.children = children;
    }
}
