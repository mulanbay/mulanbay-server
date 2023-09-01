package cn.mulanbay.pms.util;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.util.bean.PeriodDateBean;

import java.util.Date;

/**
 * 运营时间处理类
 *
 * @author fenghong
 * @create 2018-02-17
 */
public class BussDayUtil {

    /**
     * 计算周期
     *
     * @param bussDay
     * @param period
     * @return
     */
    public static PeriodDateBean calPeriod(Date bussDay,PeriodType period){
        PeriodDateBean bean = new PeriodDateBean();
        String dateFormat = "yyyy-MM";
        int totalDays =0;
        Date startDate;
        Date endDate;
        if (PeriodType.YEARLY == period) {
            dateFormat = "yyyy";
            totalDays = DateUtil.getYearDays(bussDay);
            startDate = DateUtil.getYearFirst(bussDay);
            endDate = DateUtil.getLastDayOfYear(bussDay);
        }else{
            totalDays = DateUtil.getMonthDays(bussDay);
            startDate = DateUtil.getFirstDayOfMonth(bussDay);
            endDate = DateUtil.getLastDayOfMonth(bussDay);
        }
        bean.setPeriod(period);
        bean.setDateFormat(dateFormat);
        bean.setStartDate(startDate);
        bean.setEndDate(endDate);
        bean.setTotalDays(totalDays);
        return bean;
    }

}
