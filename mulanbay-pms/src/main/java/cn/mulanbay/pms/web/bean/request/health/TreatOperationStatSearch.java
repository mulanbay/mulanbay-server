package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class TreatOperationStatSearch extends PageSearch implements BindUser {

    @Query(fieldName = "treatOperation.name", op = Parameter.Operator.LIKE)
    private String keywords;

    @Query(fieldName = "treatOperation.name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "treatOperation.treat_date", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "treatOperation.treat_date", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "treatOperation.user_id", op = Parameter.Operator.EQ)
    private Long userId;

    private String groupField;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

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

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }
}
