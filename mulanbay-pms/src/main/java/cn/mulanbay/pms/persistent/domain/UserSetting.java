package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.Payment;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 用户配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "user_setting")
public class UserSetting implements java.io.Serializable {

    private static final long serialVersionUID = -785812508054385206L;

    private Long id;
    private Long userId;
    private Boolean sendEmail;
    private Boolean sendWxMessage;
    //累计积分值
    private Boolean statScore;
    //评分的配置组，对应的是ScoreConfig中的key
    private String scoreGroup;
    //常住城市
    private String residentCity;
    //看病记录商品类型
    private Integer treatGoodsTypeId;
    //看病记录商品子类型
    private Integer treatSubGoodsTypeId;
    //看病记录购买来源
    private Integer treatBuyTypeId;
    //默认支付方式
    private Payment payment;
    //默认购买来源
    private Integer buyTypeId;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id", unique = true, nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "send_email")
    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    @Basic
    @Column(name = "send_wx_message")
    public Boolean getSendWxMessage() {
        return sendWxMessage;
    }

    public void setSendWxMessage(Boolean sendWxMessage) {
        this.sendWxMessage = sendWxMessage;
    }

    @Basic
    @Column(name = "stat_score")
    public Boolean getStatScore() {
        return statScore;
    }

    public void setStatScore(Boolean statScore) {
        this.statScore = statScore;
    }

    @Basic
    @Column(name = "score_group")
    public String getScoreGroup() {
        return scoreGroup;
    }

    public void setScoreGroup(String scoreGroup) {
        this.scoreGroup = scoreGroup;
    }

    @Basic
    @Column(name = "resident_city")
    public String getResidentCity() {
        return residentCity;
    }

    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }

    @Basic
    @Column(name = "treat_goods_type_id")
    public Integer getTreatGoodsTypeId() {
        return treatGoodsTypeId;
    }

    public void setTreatGoodsTypeId(Integer treatGoodsTypeId) {
        this.treatGoodsTypeId = treatGoodsTypeId;
    }

    @Basic
    @Column(name = "treat_sub_goods_type_id")
    public Integer getTreatSubGoodsTypeId() {
        return treatSubGoodsTypeId;
    }

    public void setTreatSubGoodsTypeId(Integer treatSubGoodsTypeId) {
        this.treatSubGoodsTypeId = treatSubGoodsTypeId;
    }

    @Basic
    @Column(name = "treat_buy_type_id")
    public Integer getTreatBuyTypeId() {
        return treatBuyTypeId;
    }

    public void setTreatBuyTypeId(Integer treatBuyTypeId) {
        this.treatBuyTypeId = treatBuyTypeId;
    }

    @Basic
    @Column(name = "payment")
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "buy_type_id")
    public Integer getBuyTypeId() {
        return buyTypeId;
    }

    public void setBuyTypeId(Integer buyTypeId) {
        this.buyTypeId = buyTypeId;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


}
