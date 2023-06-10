package cn.mulanbay.pms.persistent.dto;

public class TreatRecordUnionDto {

    private String hospital;
    private String department;
    private String organ;
    private String disease;
    private String diagnosedDisease;
    private String drugName;
    private String operationName;

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

    public String getDiagnosedDisease() {
        return diagnosedDisease;
    }

    public void setDiagnosedDisease(String diagnosedDisease) {
        this.diagnosedDisease = diagnosedDisease;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
