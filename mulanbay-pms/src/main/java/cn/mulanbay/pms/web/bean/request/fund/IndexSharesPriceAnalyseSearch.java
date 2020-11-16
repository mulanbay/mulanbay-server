package cn.mulanbay.pms.web.bean.request.fund;

import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.persistent.query.QueryBuilder;

import java.util.Date;

public class IndexSharesPriceAnalyseSearch extends QueryBuilder implements FullEndDateTime {

    @Query(fieldName = "code", op = Parameter.Operator.EQ)
    private String code;

    @Query(fieldName = "occurTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "occurTime", op = Parameter.Operator.LTE)
    private Date endDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
