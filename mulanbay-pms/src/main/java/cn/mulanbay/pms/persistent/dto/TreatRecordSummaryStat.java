package cn.mulanbay.pms.persistent.dto;

import cn.mulanbay.pms.web.bean.response.chart.ChartPieData;

public class TreatRecordSummaryStat {

    private Long totalCount;

    private Double totalRegisteredFee;

    private Double totalDrugFee;

    private Double totalOperationFee;

    private Double totalTotalFee;

    private Double totalMedicalInsurancePaidFee;

    private Double totalPersonalPaidFee;

    //统计分析图：医保和个人支付比例
    private ChartPieData pieData;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Double getTotalRegisteredFee() {
        return totalRegisteredFee;
    }

    public void setTotalRegisteredFee(Double totalRegisteredFee) {
        this.totalRegisteredFee = totalRegisteredFee;
    }

    public Double getTotalDrugFee() {
        return totalDrugFee;
    }

    public void setTotalDrugFee(Double totalDrugFee) {
        this.totalDrugFee = totalDrugFee;
    }

    public Double getTotalOperationFee() {
        return totalOperationFee;
    }

    public void setTotalOperationFee(Double totalOperationFee) {
        this.totalOperationFee = totalOperationFee;
    }

    public Double getTotalTotalFee() {
        return totalTotalFee;
    }

    public void setTotalTotalFee(Double totalTotalFee) {
        this.totalTotalFee = totalTotalFee;
    }

    public Double getTotalMedicalInsurancePaidFee() {
        return totalMedicalInsurancePaidFee;
    }

    public void setTotalMedicalInsurancePaidFee(Double totalMedicalInsurancePaidFee) {
        this.totalMedicalInsurancePaidFee = totalMedicalInsurancePaidFee;
    }

    public Double getTotalPersonalPaidFee() {
        return totalPersonalPaidFee;
    }

    public void setTotalPersonalPaidFee(Double totalPersonalPaidFee) {
        this.totalPersonalPaidFee = totalPersonalPaidFee;
    }

    public ChartPieData getPieData() {
        return pieData;
    }

    public void setPieData(ChartPieData pieData) {
        this.pieData = pieData;
    }
}
