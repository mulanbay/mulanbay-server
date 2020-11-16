package cn.mulanbay.pms.web.bean.request.chart;

import cn.mulanbay.common.aop.BindUser;

public class UserChartTreeSearch implements BindUser {

    private Boolean needRoot;

    private Long userId;

    private String relatedBeans;

    public Boolean getNeedRoot() {
        return needRoot;
    }

    public void setNeedRoot(Boolean needRoot) {
        this.needRoot = needRoot;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }

}
