package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;

import java.util.Date;

public class BuyRecordRelationSearch  implements BindUser, FullEndDateTime {

    private Long userId;

    private String tags;

    private Date startDate;

    private Date endDate;

    private Boolean unionAll;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getUnionAll() {
        return unionAll;
    }

    public void setUnionAll(Boolean unionAll) {
        this.unionAll = unionAll;
    }
}
