package cn.mulanbay.pms.web.bean.request.health;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.CrossType;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class TreatOperationSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "treatRecord.hospital,treatRecord.department,treatRecord.organ,treatRecord.disease",
            op = Parameter.Operator.LIKE, crossType = CrossType.OR)
    private String keywords;

    @Query(fieldName = "treatRecord.tags", op = Parameter.Operator.EQ)
    private String tags;

    @Query(fieldName = "name", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "treatDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "treatDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "treatRecord.userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "treatRecord.id", op = Parameter.Operator.EQ)
    private Long treatRecordId;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public Long getTreatRecordId() {
        return treatRecordId;
    }

    public void setTreatRecordId(Long treatRecordId) {
        this.treatRecordId = treatRecordId;
    }
}
