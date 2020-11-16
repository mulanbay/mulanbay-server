package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.pms.persistent.enums.PlanType;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;

public class UserPlanTreeSearch extends CommonTreeSearch {

    private PlanType planType;

    private String relatedBeans;

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }
}
