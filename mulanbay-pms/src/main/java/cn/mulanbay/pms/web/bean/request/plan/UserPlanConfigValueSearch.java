package cn.mulanbay.pms.web.bean.request.plan;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

public class UserPlanConfigValueSearch extends PageSearch {
    @Query(fieldName = "userPlan.id", op = Parameter.Operator.EQ)
    private Long userPlanId;

    @Query(fieldName = "year", op = Parameter.Operator.EQ)
    private Integer year;

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
