package cn.mulanbay.pms.web.bean.request.userbehavior;

import cn.mulanbay.common.aop.BindUserLevel;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;

public class UserBehaviorConfigToUserTreeSearch extends CommonTreeSearch implements BindUserLevel {

    private Integer level;

    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }
}
