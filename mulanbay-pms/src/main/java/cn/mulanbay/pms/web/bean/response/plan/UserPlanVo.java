package cn.mulanbay.pms.web.bean.response.plan;

import cn.mulanbay.pms.persistent.domain.PlanReport;
import cn.mulanbay.pms.persistent.domain.UserPlan;

public class UserPlanVo extends UserPlan {

    //用于最新统计
    private PlanReport planReport;

    /**
     * 次数预测
     */
    private Double predictCount;

    /**
     * 计划值预测
     */
    private Double predictValue;

    public PlanReport getPlanReport() {
        return planReport;
    }

    public void setPlanReport(PlanReport planReport) {
        this.planReport = planReport;
    }

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
