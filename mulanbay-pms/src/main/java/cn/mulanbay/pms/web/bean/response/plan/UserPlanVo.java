package cn.mulanbay.pms.web.bean.response.plan;

import cn.mulanbay.pms.persistent.domain.PlanReport;
import cn.mulanbay.pms.persistent.domain.UserPlan;

public class UserPlanVo extends UserPlan {

    //用于最新统计
    private PlanReport planReport;

    /**
     * 次数预测
     */
    private Float predictCount;

    /**
     * 计划值预测
     */
    private Float predictValue;

    public PlanReport getPlanReport() {
        return planReport;
    }

    public void setPlanReport(PlanReport planReport) {
        this.planReport = planReport;
    }

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
