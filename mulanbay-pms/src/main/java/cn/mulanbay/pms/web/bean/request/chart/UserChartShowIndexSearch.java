package cn.mulanbay.pms.web.bean.request.chart;

import cn.mulanbay.common.aop.BindUser;

public class UserChartShowIndexSearch implements BindUser {

    private Long userId;

    //查找第几个
    private Integer index;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
