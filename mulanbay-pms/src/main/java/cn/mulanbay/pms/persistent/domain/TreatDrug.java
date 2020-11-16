package cn.mulanbay.pms.persistent.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 药品记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "treat_drug")
public class TreatDrug implements java.io.Serializable {
    private static final long serialVersionUID = 1598040961232804958L;
    private Long id;
    private TreatRecord treatRecord;
    private Long userId;
    private String name;
    private String unit;
    private Integer amount;
    private String disease;
    private Double unitPrice;
    private Double totalPrice;
    //每多少天一次
    private Integer perDay;
    private Integer perTimes;
    //每次食用的单位
    private String eu;
    //每次食用的量
    private Integer ec;
    private String useWay;
    private Date treatDate;
    //开始于结束用药时间
    private Date beginDate;
    private Date endDate;
    //药是否有效
    private Boolean available;
    private Boolean remind;
    private String remark;
    private Date createdTime;
    private Date lastModifyTime;

    public TreatDrug() {
    }

    public TreatDrug(Long id, String name, String unit, Integer perDay, Integer perTimes, String eu, Integer ec, String useWay, Date beginDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.perDay = perDay;
        this.perTimes = perTimes;
        this.eu = eu;
        this.ec = ec;
        this.useWay = useWay;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "treat_record_id")
    public TreatRecord getTreatRecord() {
        return treatRecord;
    }

    public void setTreatRecord(TreatRecord treatRecord) {
        this.treatRecord = treatRecord;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "disease")
    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Basic
    @Column(name = "unit_price")
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "total_price")
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Basic
    @Column(name = "per_day")
    public Integer getPerDay() {
        return perDay;
    }

    public void setPerDay(Integer perDay) {
        this.perDay = perDay;
    }

    @Basic
    @Column(name = "per_times")
    public Integer getPerTimes() {
        return perTimes;
    }

    public void setPerTimes(Integer perTimes) {
        this.perTimes = perTimes;
    }

    @Basic
    @Column(name = "eu")
    public String getEu() {
        return eu;
    }

    public void setEu(String eu) {
        this.eu = eu;
    }

    @Basic
    @Column(name = "ec")
    public Integer getEc() {
        return ec;
    }

    public void setEc(Integer ec) {
        this.ec = ec;
    }

    @Basic
    @Column(name = "use_way")
    public String getUseWay() {
        return useWay;
    }

    public void setUseWay(String useWay) {
        this.useWay = useWay;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "treat_date", length = 10)
    public Date getTreatDate() {
        return treatDate;
    }

    public void setTreatDate(Date treatDate) {
        this.treatDate = treatDate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "begin_date", length = 10)
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", length = 10)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "available")
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Basic
    @Column(name = "remind")
    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
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
