package cn.mulanbay.pms.handler.bean;

public class ConsumeBean {

    //普通消费
    private double ncAmount;
    //突发消费
    private double bcAmount;
    //看病
    private double treatAmount;

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

    public double getTreatAmount() {
        return treatAmount;
    }

    public void setTreatAmount(double treatAmount) {
        this.treatAmount = treatAmount;
    }
}
