package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.TreatType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TreatRecordFormRequest implements BindUser {

    private Long id;
    private Long userId;

    @NotNull(message = "{validate.treatRecord.treatType.NotNull}")
    private TreatType treatType;
    //医院
    @NotEmpty(message = "{validate.treatRecord.hospital.notEmpty}")
    private String hospital;
    // 科室
    @NotEmpty(message = "{validate.treatRecord.department.notEmpty}")
    private String department;
    //器官
    @NotEmpty(message = "{validate.treatRecord.organ.notEmpty}")
    private String organ;
    // 疾病
    @NotEmpty(message = "{validate.treatRecord.disease.notEmpty}")
    private String disease;
    // 疼痛级别(1-10)
    @NotNull(message = "{validate.treatRecord.painLevel.NotNull}")
    private Integer painLevel;
    // 重要等级(0-5)
    @NotNull(message = "{validate.treatRecord.importantLevel.NotNull}")
    private Double importantLevel;
    // 确诊疾病
    //@NotEmpty(message = "{validate.treatRecord.diagnosedDisease.notEmpty}")
    private String diagnosedDisease;
    // 是否有病
    @NotNull(message = "{validate.treatRecord.isSick.NotNull}")
    private Boolean isSick;
    // 看病日期
    @NotNull(message = "{validate.treatRecord.treatDate.NotNull}")
    private Date treatDate;
    // 门诊类型
    @NotEmpty(message = "{validate.treatRecord.osName.NotEmpty}")
    private String osName;
    //医生名字
    private String doctor;
    // 挂号费（原价:个人+医保）
    @NotNull(message = "{validate.treatRecord.registeredFee.NotNull}")
    private Double registeredFee;
    // 药费（原价:个人+医保）
    @NotNull(message = "{validate.treatRecord.drugFee.NotNull}")
    private Double drugFee;
    // 手术费用（原价:个人+医保）
    @NotNull(message = "{validate.treatRecord.operationFee.NotNull}")
    private Double operationFee;
    // 总共花费
    @NotNull(message = "{validate.treatRecord.totalFee.NotNull}")
    private Double totalFee;
    // 医保花费
    @NotNull(message = "{validate.treatRecord.medicalInsurancePaidFee.NotNull}")
    private Double medicalInsurancePaidFee;
    // 个人支付费用
    @NotNull(message = "{validate.treatRecord.personalPaidFee.NotNull}")
    private Double personalPaidFee;
    private String tags;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TreatType getTreatType() {
        return treatType;
    }

    public void setTreatType(TreatType treatType) {
        this.treatType = treatType;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Integer getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(Integer painLevel) {
        this.painLevel = painLevel;
    }

    public Double getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(Double importantLevel) {
        this.importantLevel = importantLevel;
    }

    public String getDiagnosedDisease() {
        return diagnosedDisease;
    }

    public void setDiagnosedDisease(String diagnosedDisease) {
        this.diagnosedDisease = diagnosedDisease;
    }

    public Boolean getIsSick() {
        return isSick;
    }

    public void setIsSick(Boolean isSick) {
        this.isSick = isSick;
    }

    public Date getTreatDate() {
        return treatDate;
    }

    public void setTreatDate(Date treatDate) {
        this.treatDate = treatDate;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Double getRegisteredFee() {
        return registeredFee;
    }

    public void setRegisteredFee(Double registeredFee) {
        this.registeredFee = registeredFee;
    }

    public Double getDrugFee() {
        return drugFee;
    }

    public void setDrugFee(Double drugFee) {
        this.drugFee = drugFee;
    }

    public Double getOperationFee() {
        return operationFee;
    }

    public void setOperationFee(Double operationFee) {
        this.operationFee = operationFee;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getMedicalInsurancePaidFee() {
        return medicalInsurancePaidFee;
    }

    public void setMedicalInsurancePaidFee(Double medicalInsurancePaidFee) {
        this.medicalInsurancePaidFee = medicalInsurancePaidFee;
    }

    public Double getPersonalPaidFee() {
        return personalPaidFee;
    }

    public void setPersonalPaidFee(Double personalPaidFee) {
        this.personalPaidFee = personalPaidFee;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
