package cn.mulanbay.pms.web.bean.response.plan;

import cn.mulanbay.pms.persistent.domain.PlanReport;

public class PlanReportPredictVo extends PlanReport {

    /**
     * 次数预测
     */
    private Double predictCount;

    /**
     * 计划值预测
     */
    private Double predictValue;

    public Double getPredictCount() {
        return predictCount;
    }

    public void setPredictCount(Double predictCount) {
        this.predictCount = predictCount;
    }

    public Double getPredictValue() {
        return predictValue;
    }

    public void setPredictValue(Double predictValue) {
        this.predictValue = predictValue;
    }
}
