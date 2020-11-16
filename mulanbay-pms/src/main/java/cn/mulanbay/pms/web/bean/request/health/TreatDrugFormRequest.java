package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TreatDrugFormRequest implements BindUser {

    private Long id;

    @NotNull(message = "{validate.treatDrug.treatRecordId.NotNull}")
    private Long treatRecordId;

    private Long userId;

    @NotEmpty(message = "{validate.treatDrug.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{validate.treatDrug.unit.notEmpty}")
    private String unit;

    @NotNull(message = "{validate.treatDrug.amount.NotNull}")
    private Integer amount;

    @NotEmpty(message = "{validate.treatDrug.disease.NotEmpty}")
    private String disease;

    @NotNull(message = "{validate.treatDrug.unitPrice.NotNull}")
    private Double unitPrice;

    private Double totalPrice;
    //每多少天一次
    @NotNull(message = "{validate.treatDrug.perDay.NotNull}")
    private Integer perDay;

    @NotNull(message = "{validate.treatDrug.perTimes.NotNull}")
    private Integer perTimes;

    //每次食用的单位
    //@NotEmpty(message = "{validate.treatDrug.eu.NotEmpty}")
    private String eu;

    //@NotNull(message = "{validate.treatDrug.ec.NotNull}")
    private Integer ec;

    @NotEmpty(message = "{validate.treatDrug.useWay.NotEmpty}")
    private String useWay;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.treatDrug.treatDate.NotNull}")
    private Date treatDate;

    //开始于结束用药时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.treatDrug.beginDate.NotNull}")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validate.treatDrug.endDate.NotNull}")
    private Date endDate;

    //药是否有效
    @NotNull(message = "{validate.treatDrug.available.NotNull}")
    private Boolean available;

    @NotNull(message = "{validate.treatDrug.remind.NotNull}")
    private Boolean remind;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTreatRecordId() {
        return treatRecordId;
    }

    public void setTreatRecordId(Long treatRecordId) {
        this.treatRecordId = treatRecordId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPerDay() {
        return perDay;
    }

    public void setPerDay(Integer perDay) {
        this.perDay = perDay;
    }

    public Integer getPerTimes() {
        return perTimes;
    }

    public void setPerTimes(Integer perTimes) {
        this.perTimes = perTimes;
    }

    public String getEu() {
        return eu;
    }

    public void setEu(String eu) {
        this.eu = eu;
    }

    public Integer getEc() {
        return ec;
    }

    public void setEc(Integer ec) {
        this.ec = ec;
    }

    public String getUseWay() {
        return useWay;
    }

    public void setUseWay(String useWay) {
        this.useWay = useWay;
    }

    public Date getTreatDate() {
        return treatDate;
    }

    public void setTreatDate(Date treatDate) {
        this.treatDate = treatDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
