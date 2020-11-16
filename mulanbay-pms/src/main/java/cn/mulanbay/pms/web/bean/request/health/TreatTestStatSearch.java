package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;
import javax.validation.constraints.NotEmpty;

import java.util.Date;

public class TreatTestStatSearch extends PageSearch implements FullEndDateTime, BindUser {

    @Query(fieldName = "testDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "testDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "treatOperation.id", op = Parameter.Operator.EQ)
    private Long treatOperationId;

    @NotEmpty(message = "{validate.treatTest.name.NotEmpty}")
    @Query(fieldName = "name", op = Parameter.Operator.EQ)
    private String name;

    @Query(fieldName = "result", op = Parameter.Operator.EQ)
    private Integer result;

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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

}
