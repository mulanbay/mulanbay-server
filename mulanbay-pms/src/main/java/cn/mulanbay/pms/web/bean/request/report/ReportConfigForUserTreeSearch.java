package cn.mulanbay.pms.web.bean.request.report;

import cn.mulanbay.common.aop.BindUserLevel;

public class ReportConfigForUserTreeSearch implements BindUserLevel {

    private Boolean needRoot;

    private Integer level;

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }
}
