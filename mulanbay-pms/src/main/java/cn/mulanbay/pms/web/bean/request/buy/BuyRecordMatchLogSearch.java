package cn.mulanbay.pms.web.bean.request.buy;

import cn.mulanbay.persistent.query.NullType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class BuyRecordMatchLogSearch extends PageSearch {

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "createdTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "createdTime", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "compareId", op = Parameter.Operator.NULL_NOTNULL)
    private NullType compareIdType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public NullType getCompareIdType() {
        return compareIdType;
    }

    public void setCompareIdType(NullType compareIdType) {
        this.compareIdType = compareIdType;
    }
}
