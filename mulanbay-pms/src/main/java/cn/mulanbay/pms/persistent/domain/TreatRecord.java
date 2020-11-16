package cn.mulanbay.pms.persistent.domain;

import cn.mulanbay.pms.persistent.enums.TreatType;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 看病记录
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Entity
@Table(name = "treat_record")
public class TreatRecord implements java.io.Serializable {
    private static final long serialVersionUID = 4680164191879433281L;
    private Long id;
    private Long userId;

    private TreatType treatType;
    //医院
    private String hospital;
    // 科室
    private String department;
    //器官
    private String organ;
    // 疾病症状
    private String disease;
    // 疼痛级别(1-10)
    private Integer painLevel;
    // 重要等级(0-5)
    private Double importantLevel;
    // 确诊疾病
    private String diagnosedDisease;
    // 是否有病
    private Boolean isSick;
    // 看病日期
    private Date treatDate;
    // 门诊类型(普通、专家、急诊)
    private String osName;
    //医生名字
    private String doctor;
    // 挂号费（原价:个人+医保）
    private Double registeredFee;
    // 药费（原价:个人+医保）
    private Double drugFee;
    // 手术费用（原价:个人+医保）
    private Double operationFee;
    // 总共花费
    private Double totalFee;
    // 医保花费
    private Double medicalInsurancePaidFee;
    // 个人支付费用
    private Double personalPaidFee;
    private String tags;
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

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "treat_type")
    public TreatType getTreatType() {
        return treatType;
    }

    public void setTreatType(TreatType treatType) {
        this.treatType = treatType;
    }

    @Basic
    @Column(name = "hospital")
    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    @Basic
    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "organ")
    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
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
    @Column(name = "pain_level")
    public Integer getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(Integer painLevel) {
        this.painLevel = painLevel;
    }

    @Basic
    @Column(name = "important_level")
    public Double getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(Double importantLevel) {
        this.importantLevel = importantLevel;
    }

    @Basic
    @Column(name = "diagnosed_disease")
    public String getDiagnosedDisease() {
        return diagnosedDisease;
    }

    public void setDiagnosedDisease(String diagnosedDisease) {
        this.diagnosedDisease = diagnosedDisease;
    }

    @Basic
    @Column(name = "is_sick")
    public Boolean getIsSick() {
        return isSick;
    }

    public void setIsSick(Boolean isSick) {
        this.isSick = isSick;
    }

    @Basic
    @Column(name = "treat_date", length = 10)
    public Date getTreatDate() {
        return treatDate;
    }

    public void setTreatDate(Date treatDate) {
        this.treatDate = treatDate;
    }

    @Basic
    @Column(name = "os_name")
    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    @Basic
    @Column(name = "doctor")
    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    @Basic
    @Column(name = "registered_fee")
    public Double getRegisteredFee() {
        return registeredFee;
    }

    public void setRegisteredFee(Double registeredFee) {
        this.registeredFee = registeredFee;
    }

    @Basic
    @Column(name = "drug_fee")
    public Double getDrugFee() {
        return drugFee;
    }

    public void setDrugFee(Double drugFee) {
        this.drugFee = drugFee;
    }

    @Basic
    @Column(name = "operation_fee")
    public Double getOperationFee() {
        return operationFee;
    }

    public void setOperationFee(Double operationFee) {
        this.operationFee = operationFee;
    }

    @Basic
    @Column(name = "total_fee")
    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    @Basic
    @Column(name = "medical_insurance_paid_fee")
    public Double getMedicalInsurancePaidFee() {
        return medicalInsurancePaidFee;
    }

    public void setMedicalInsurancePaidFee(Double medicalInsurancePaidFee) {
        this.medicalInsurancePaidFee = medicalInsurancePaidFee;
    }

    @Basic
    @Column(name = "personal_paid_fee")
    public Double getPersonalPaidFee() {
        return personalPaidFee;
    }

    public void setPersonalPaidFee(Double personalPaidFee) {
        this.personalPaidFee = personalPaidFee;
    }

    @Basic
    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    @Transient
    public String getTreatTypeName() {
        return treatType == null ? null : treatType.getName();
    }
}
