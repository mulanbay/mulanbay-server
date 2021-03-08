package cn.mulanbay.pms.web.bean.response.fund;

import cn.mulanbay.common.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

public class BudgetChildrenVo  {

    private Double budgetAmount;

    private Double cpPaidAmount;

    private String bussKey;

    private List<BudgetDetailVo> children;

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

    /**
     * 比例
     * @return
     */
    public double getRate(){
        return NumberUtil.getPercentValue(this.cpPaidAmount,this.budgetAmount,2);
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
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
