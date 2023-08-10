package cn.mulanbay.pms.web.bean.response.plan;

import cn.mulanbay.pms.persistent.domain.PlanReport;

public class PlanReportPredictVo extends PlanReport {

    /**
     * 次数预测
     */
    private Float predictCount;

    /**
     * 计划值预测
     */
    private Float predictValue;

    public Float getPredictCount() {
        return predictCount;
    }

    public void setPredictCount(Float predictCount) {
        this.predictCount = predictCount;
    }

    public Float getPredictValue() {
        return predictValue;
    }

    public void setPredictValue(Float predictValue) {
        this.predictValue = predictValue;
    }
}
