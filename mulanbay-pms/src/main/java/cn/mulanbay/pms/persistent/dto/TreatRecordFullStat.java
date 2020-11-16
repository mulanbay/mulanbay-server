package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class TreatRecordFullStat {

    private Long id;

    private String tags;

    private BigInteger totalCount;

    private Date minTreatDate;

    private Date maxTreatDate;

    // 挂号费（原价:个人+医保）
    private BigDecimal registeredFee;
    // 药费（原价:个人+医保）
    private BigDecimal drugFee;
    // 手术费用（原价:个人+医保）
    private BigDecimal operationFee;
    // 总共花费
    private BigDecimal totalFee;
    // 医保花费
    private BigDecimal medicalInsurancePaidFee;
    // 个人支付费用
    private BigDecimal personalPaidFee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public Date getMinTreatDate() {
        return minTreatDate;
    }

    public void setMinTreatDate(Date minTreatDate) {
        this.minTreatDate = minTreatDate;
    }

    public Date getMaxTreatDate() {
        return maxTreatDate;
    }

    public void setMaxTreatDate(Date maxTreatDate) {
        this.maxTreatDate = maxTreatDate;
    }

    public BigDecimal getRegisteredFee() {
        return registeredFee;
    }

    public void setRegisteredFee(BigDecimal registeredFee) {
        this.registeredFee = registeredFee;
    }

    public BigDecimal getDrugFee() {
        return drugFee;
    }

    public void setDrugFee(BigDecimal drugFee) {
        this.drugFee = drugFee;
    }

    public BigDecimal getOperationFee() {
        return operationFee;
    }

    public void setOperationFee(BigDecimal operationFee) {
        this.operationFee = operationFee;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getMedicalInsurancePaidFee() {
        return medicalInsurancePaidFee;
    }

    public void setMedicalInsurancePaidFee(BigDecimal medicalInsurancePaidFee) {
        this.medicalInsurancePaidFee = medicalInsurancePaidFee;
    }

    public BigDecimal getPersonalPaidFee() {
        return personalPaidFee;
    }

    public void setPersonalPaidFee(BigDecimal personalPaidFee) {
        this.personalPaidFee = personalPaidFee;
    }
}
