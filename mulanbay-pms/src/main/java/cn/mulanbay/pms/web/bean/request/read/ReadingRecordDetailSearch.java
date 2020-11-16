package cn.mulanbay.pms.web.bean.request.read;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.common.aop.FullEndDateTime;
import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;
import cn.mulanbay.web.bean.request.PageSearch;

import java.util.Date;

public class ReadingRecordDetailSearch extends PageSearch implements BindUser, FullEndDateTime {

    @Query(fieldName = "readingRecord.bookName", op = Parameter.Operator.LIKE)
    private String name;

    @Query(fieldName = "readTime", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "readTime", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    public Long userId;

    @Query(fieldName = "readingRecord.id", op = Parameter.Operator.EQ)
    private Long readingRecordId;

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

    public Long getReadingRecordId() {
        return readingRecordId;
    }

    public void setReadingRecordId(Long readingRecordId) {
        this.readingRecordId = readingRecordId;
    }
}
