package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;
import java.util.Date;

public class TreatDrugDetailStat {

    private BigInteger id;

    private String name;

    private Date treatDate;

    private Date minTime;

    private Date maxTime;

    private Object totalCount;

    //用药天数
    private Object days;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTreatDate() {
        return treatDate;
    }

    public void setTreatDate(Date treatDate) {
        this.treatDate = treatDate;
    }

    public Object getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Object totalCount) {
        this.totalCount = totalCount;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }

    public Object getDays() {
        return days;
    }

    public void setDays(Object days) {
        this.days = days;
    }

    /**
     * 不同的sql会产生不同的绑定对象类型
     *
     * @return
     */
    public long getTotalCountLong() {
        return Long.valueOf(totalCount.toString());
    }

    /**
     * 不同的sql会产生不同的绑定对象类型
     *
     * @return
     */
    public long getDaysLong() {
        return Long.valueOf(days.toString());
    }
}
