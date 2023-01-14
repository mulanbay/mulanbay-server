package cn.mulanbay.pms.persistent.dto.common;

/**
 * 天(日历)统计封装
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public interface CalendarDateStat {

    /**
     * 获取统计值
     *
     * @return
     */
    double getCalendarStatValue();

    /**
     * 获取天的索引值
     *
     * @return
     */
    int getDayIndexValue();
}
