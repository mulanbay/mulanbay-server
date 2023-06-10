package cn.mulanbay.pms.web.bean.request.user;

import cn.mulanbay.pms.persistent.enums.Payment;

public class UserSettingFormRequest {

    private Boolean sendEmail;
    private Boolean sendWxMessage;
    //评分的配置组，对应的是ScoreConfig中的key
    private String scoreGroup;
    //常住城市
    private String residentCity;
    //看病记录商品类型
    private Integer treatGoodsTypeId;
    //看病记录商品子类型
    private Integer treatSubGoodsTypeId;
    private Integer treatBuyTypeId;
    //默认支付方式
    private Payment payment;
    //默认购买来源
    private Integer buyTypeId;
    private String remark;

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Boolean getSendWxMessage() {
        return sendWxMessage;
    }

    public void setSendWxMessage(Boolean sendWxMessage) {
        this.sendWxMessage = sendWxMessage;
    }

    public String getScoreGroup() {
        return scoreGroup;
    }

    public void setScoreGroup(String scoreGroup) {
        this.scoreGroup = scoreGroup;
    }

    public String getResidentCity() {
        return residentCity;
    }

    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }

    public Integer getTreatGoodsTypeId() {
        return treatGoodsTypeId;
    }

    public void setTreatGoodsTypeId(Integer treatGoodsTypeId) {
        this.treatGoodsTypeId = treatGoodsTypeId;
    }

    public Integer getTreatSubGoodsTypeId() {
        return treatSubGoodsTypeId;
    }

    public void setTreatSubGoodsTypeId(Integer treatSubGoodsTypeId) {
        this.treatSubGoodsTypeId = treatSubGoodsTypeId;
    }

    public Integer getTreatBuyTypeId() {
        return treatBuyTypeId;
    }

    public void setTreatBuyTypeId(Integer treatBuyTypeId) {
        this.treatBuyTypeId = treatBuyTypeId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Integer getBuyTypeId() {
        return buyTypeId;
    }

    public void setBuyTypeId(Integer buyTypeId) {
        this.buyTypeId = buyTypeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
