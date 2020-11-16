package cn.mulanbay.pms.web.bean.request.music;

import cn.mulanbay.persistent.query.Parameter;
import cn.mulanbay.persistent.query.Query;

import java.util.Date;

public class MusicPracticeMonthStatSearch {

    @Query(fieldName = "userId", op = Parameter.Operator.EQ)
    private Long userId;

    @Query(fieldName = "practiceDate", op = Parameter.Operator.GTE)
    private Date startDate;

    @Query(fieldName = "practiceDate", op = Parameter.Operator.LTE)
    private Date endDate;

    //年份，通过年份再重新设置开始和结束日期
    private Integer year;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
