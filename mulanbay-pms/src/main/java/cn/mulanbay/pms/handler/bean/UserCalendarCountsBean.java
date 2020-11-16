package cn.mulanbay.pms.handler.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户日历
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class UserCalendarCountsBean implements Serializable {

    private static final long serialVersionUID = -1819813255375436212L;

    private int counts;

    private Date date;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
