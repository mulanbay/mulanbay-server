package cn.mulanbay.pms.web.bean.response.calendar;

import cn.mulanbay.pms.persistent.domain.UserCalendar;

public class UserCalendarVo extends UserCalendar {

    private String value;

    private String unit;

    /**
     * 日志流水是否和原日历设置一样
     */
    private boolean match = true;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }
}
