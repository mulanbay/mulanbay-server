package cn.mulanbay.pms.web.bean.response.plan;

import cn.mulanbay.pms.persistent.domain.PlanConfig;
import cn.mulanbay.pms.persistent.domain.PlanReport;

public class PlanConfigVo extends PlanConfig {

    //用于最新统计
    private PlanReport planReport;

    public PlanReport getPlanReport() {
        return planReport;
    }

    public void setPlanReport(PlanReport planReport) {
        this.planReport = planReport;
    }

}
