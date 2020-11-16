package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.pms.persistent.enums.TreatTestResult;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class TreatTestSearch extends PageSearch implements FullEndDateTime, BindUser {

    @Query(fieldName = "treatOperation.treatRecord.tags", op = Parameter.Operator.EQ)
    private String tags;

    @Query(fieldName = "testDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "testDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "treatOperation.id", op = Parameter.Operator.EQ)
    private Long treatOperationId;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "result", op = Parameter.Operator.EQ)
    private TreatTestResult result;

    private String sort = "desc";

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

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTreatOperationId() {
        return treatOperationId;
    }

    public void setTreatOperationId(Long treatOperationId) {
        this.treatOperationId = treatOperationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreatTestResult getResult() {
        return result;
    }

    public void setResult(TreatTestResult result) {
        this.result = result;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
