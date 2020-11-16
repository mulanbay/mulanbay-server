package cn.mulanbay.pms.web.bean.request.life;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter.Operator;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;

public class LifeExperienceSumAnalyseSearch extends QueryBuilder implements BindUser {

    @Query(fieldName = "year", op = Operator.GTE)
    private Integer startYear;

    @Query(fieldName = "year", op = Operator.LTE)
    private Integer endYear;

    @Query(fieldName = "user_id", op = Operator.EQ)
    private Long userId;

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
