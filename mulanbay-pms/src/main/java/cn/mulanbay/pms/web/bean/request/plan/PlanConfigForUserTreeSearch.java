package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.BindUserLevel;

public class PlanConfigForUserTreeSearch extends PlanConfigTreeSearch implements BindUser, BindUserLevel {

    private Integer level;

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }

}
