package cn.mulanbay.pms.handler.bean;

import java.io.Serializable;

/**
 * 用户持续操作的缓存类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class UserContinueOp implements Serializable {
    private static final long serialVersionUID = 2139963442875877148L;

    //第一次时间
    private int fistDay;

    //最近的时间
    private int lastDay;

    //持续天数
    private int days;

    public void addDay() {
        days++;
    }

    public int getFistDay() {
        return fistDay;
    }

    public void setFistDay(int fistDay) {
        this.fistDay = fistDay;
    }

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
