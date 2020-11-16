package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;

import java.util.Date;

public class MusicPracticeDayStatSearch {


    @Query(fieldName = "practiceDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "practiceDate", op = Parameter.Operator.LTE)
    private Date endDate;

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
