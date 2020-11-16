package cn.mulanbay.pms.web.bean.request.chart;

import cn.mulanbay.common.aop.BindUserLevel;

public class ChartConfigTreeSearch implements BindUserLevel {

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
