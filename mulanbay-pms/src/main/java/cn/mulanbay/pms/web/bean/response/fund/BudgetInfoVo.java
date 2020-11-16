package cn.mulanbay.pms.web.bean.response.fund;

import cn.mulanbay.pms.persistent.domain.Budget;

import java.util.Date;

public class BudgetInfoVo extends Budget {

    //发生的时间
    private Date occurDate;

    //实际普通消费金额
    private Double ncAmount;
    //实际突发消费金额
    private Double bcAmount;
    //实际看病消费金额
    private Double trAmount;
    //有些花费可以直接与消费记录挂钩
    private Long buyRecordId;

    //跟实际需要实现的还剩余几天
    private Integer leftDays;

    //时间的比例值，比如每天的在月预算里是30，在年预算里是365
    private int drate = 1;

    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Double getNcAmount() {
        return ncAmount;
    }

    public void setNcAmount(Double ncAmount) {
        this.ncAmount = ncAmount;
    }

    public Double getBcAmount() {
        return bcAmount;
    }

    public void setBcAmount(Double bcAmount) {
        this.bcAmount = bcAmount;
    }

    public Double getTrAmount() {
        return trAmount;
    }

    public void setTrAmount(Double trAmount) {
        this.trAmount = trAmount;
    }

    public Long getBuyRecordId() {
        return buyRecordId;
    }

    public void setBuyRecordId(Long buyRecordId) {
        this.buyRecordId = buyRecordId;
    }

    public Integer getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(Integer leftDays) {
        this.leftDays = leftDays;
    }

    public int getDrate() {
        return drate;
    }

    public void setDrate(int drate) {
        this.drate = drate;
    }
}
