package cn.mulanbay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期、时间处理工具类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public final static String FormatDay1 = "yyyy-MM-dd";
	public final static String FormatDay2 = "yyyy年MM月dd日";
	public final static String FormatDay3 = "yyyy年MM月dd日 HH:mm:ss";
	public final static String FormatDay4 = "yyyy-MM-dd HH:mm";

	public static final String Format24Datetime = "yyyy-MM-dd HH:mm:ss";// 24

	public static final String Format24Datetime2 = "yyyyMMddHHmmss";// 24

	public static final String Format12Datetime = "yyyy-MM-dd hh:mm:ss";// 12

	/**
	 * 两个日期间的天数
	 *
	 * @param startday
	 *            距离现在之后的天数
	 * @return Date:距离现在之后的若干天的日期;
	 */
	public static int getIntervalDays(Date startday, Date endday) {
		if(startday==null||endday==null){
			return 0;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}

	/**
	 * 取得距离现在多少天（距离现在之后的若干天）
	 *
	 * @param days
	 *            距离现在之后的天数
	 * @return Date:距离现在之后的若干天的日期(yyyy-MM-dd);
	 */
	public static Date getDate(int days) {
		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, days);
			Date d = c.getTime();
			return getDate(d, FormatDay1);
		} catch (Exception e) {
			logger.error("取得距离现在多少天异常",e);
		}
		return null;
	}

	/**
	 * 获取时间的年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date){
		if(date==null){
			return 0;
		}else{
			String s =getFormatDate(date,"yyyy");
			return Integer.valueOf(s);
		}
	}

	/**
	 * 取得距离现在多少天（距离现在之后的若干天）
	 *
	 * @param days
	 *            距离现在之后的天数
	 * @return Date:距离现在之后的若干天的日期(yyyy-MM-dd);
	 */
	public static Date getDate(int days, Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, days);
			Date d = c.getTime();
			return getDate(d, FormatDay1);
		} catch (Exception e) {
			logger.error("取得距离现在多少天异常",e);
		}
		return null;
	}

	/**
	 * 取得距离现在多少星期（距离现在之后的若干天）
	 *
	 *            距离现在之后的天数
	 * @return Date:距离现在之后的若干天的日期(yyyy-MM-dd);
	 */
	public static Date getDateWeek(int weeks, Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_WEEK, weeks);
			Date d = c.getTime();
			return getDate(d, FormatDay1);
		} catch (Exception e) {
			logger.error("取得距离现在多少天异常",e);
		}
		return null;
	}

	/**
	 * 取得距离现在多少月（距离现在之后的若干天）
	 *
	 *            距离现在之后的天数
	 * @return Date:距离现在之后的若干天的日期(yyyy-MM-dd);
	 */
	public static Date getDateMonth(int months, Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, months);
			Date d = c.getTime();
			return getDate(d, FormatDay1);
		} catch (Exception e) {
			logger.error("取得距离现在多少天异常",e);
		}
		return null;
	}

	/**
	 * 取得距离现在多少年（距离现在之后的若干天）
	 *
	 *            距离现在之后的天数
	 * @return Date:距离现在之后的若干天的日期(yyyy-MM-dd);
	 */
	public static Date getDateYear(int years, Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.YEAR, years);
			Date d = c.getTime();
			return getDate(d, FormatDay1);
		} catch (Exception e) {
			logger.error("取得距离现在多少天异常",e);
		}
		return null;
	}

	/**
	 * 获取当前时间是一年中的第几周
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date){
		return getIndexOfDate(date,Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取该天是星期几
	 * @param date
	 * @return 1,2,3,4,5,6,7
	 */
	public static int getDayIndexInWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w == 0) {
            w = 7;
        }
		return w;
	}

	/**
	 * 获取当前时间是一年中的第几月
	 * @param date
	 * @return 从0开始，0-11
	 */
	public static int getMonth(Date date){
		return getIndexOfDate(date,Calendar.MONTH);
	}

	/**
	 * 获取当前时间是该月的第几天
	 * @param date
	 * @return 从0开始，0-11
	 */
	public static int getDayOfMonth(Date date){
		return getIndexOfDate(date,Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前时间是该年的第几天
	 * @param date
	 * @return 从0开始，0-11
	 */
	public static int getDayOfYear(Date date){
		return getIndexOfDate(date,Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取当前时间的索引
	 * @param date
	 * @return
	 */
	private static int getIndexOfDate(Date date,int field){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(field);
		} catch (Exception e) {
			logger.error("获取当前时间的索引异常",e);
		}
		return 0;
	}


	/**
	 * 取得距离现在多少天（距离现在之后的若干天）
	 *
	 * @param days
	 *            距离现在之后的天数
	 * @return Date:距离现在之后的若干天的日期(yyyy-MM-dd HH:mm:ss);
	 */
	public static Date getDateTime(int days) {
		try {
			Date d = getDate(days);
			return getDate(d, Format24Datetime);
		} catch (Exception e) {
			System.out.println("exception" + e.toString());
		}
		return null;
	}
	/**
	 * @获取当前时间是星期几，“星期日”
	 * @return String
	 */
	public static String getDayInWeek() {
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
		return simpleDateFormat.format(today);
	}

	/**
	 * 获取指定月份的天数
	 * @param date 指定日期
	 * @return
	 */
	public static int getMonthDays(Date date){
		int year = getYear(date);
		int month = getMonth(date);
		Calendar   cal   =   Calendar.getInstance();
		cal.set(year,month,1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定年的天数
	 * @param date 指定日期
	 * @return
	 */
	public static int getYearDays(Date date){
		int year = getYear(date);
		int month = getMonth(date);
		Calendar   cal   =   Calendar.getInstance();
		cal.set(year,month,1);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
	}


	/**
	 * 获取指定月份的天数
	 * @param year 指定年
	 * @param month 指定月(从0开始，1-11)
	 * @return
	 */
	public static int getDayOfMonth(int year,int month){
		Calendar   cal   =   Calendar.getInstance();
		cal.set(year,month,1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @根据日期以及日期格式，获取日期字符串表达
	 * @return String
	 */
	public static String getFormatDate(java.util.Date thisDate, String format) {
		if (thisDate == null) {
            return "";
        }
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(thisDate);
	}

	/**
	 * @根据日期以及日期格式，获取日期
	 * @return String
	 */
	public static java.util.Date getDate(String date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * @根据日期以及日期格式，获取日期
	 * @return String
	 */
	public static java.util.Date getDate(Date date, String format) {
		String dateString = getFormatDate(date, format);
		return getDate(dateString, format);
	}

	/**
	 * @根据日期以及日期格式，获取日期
	 * @return String
	 */
	public static java.util.Date getCurrentFormatDate() {
		String dateString = getFormatDate(new Date(), Format24Datetime);
		return getDate(dateString, Format24Datetime);
	}

	/**
	 * 获取短时间类型时间字符串,格式为'yyyy-MM-dd'
	 *
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String getShortFormatDate(java.util.Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FormatDay1);
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取短时间类型时间字符串,格式为'yyyy-MM-dd'
	 *
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String getFormatDateString(String date,String oldFormat,String newFormat) {
		Date date1 =getDate(date,oldFormat);
		return getFormatDate(date1,newFormat);
	}

	/**
	 * 获取长时间类型时间字符串,格式为'yyyy-MM-dd'
	 *
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String getLongFormatDate(java.util.Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				Format24Datetime);
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取当前时间(精确到天)
	 *
	 * @return
	 */
	public static String getToday() {
		return getToday(FormatDay1);
	}

	/**
	 * 获取距离凌晨的当前时间
	 *
	 * @return
	 */
	public static String getFromMiddleNight() {
		return getToday(FormatDay1)+ " 00:00:00";
	}

	public static Date getFromMiddleNightDate(String date){
		return getDate(date+" 00:00:00",Format24Datetime);
	}

	public static Date getFromMiddleNightDate(Date date){
		String s = getFormatDate(date,FormatDay1);
		return getDate(s+" 00:00:00",Format24Datetime);
	}


	/**
	 * 添加午夜的23:59:59
	 * @param date
	 * @return
	 */
	public static Date getTodayTillMiddleNightDate(String date){
		return getDate(date+" 23:59:59",Format24Datetime);
	}

	/**
	 * 添加午夜的23:59:59
	 * @param date
	 * @return
	 */
	public static Date getTodayTillMiddleNightDate(Date date){
	 	String dd = DateUtil.getFormatDate(date,DateUtil.FormatDay1);
		return getDate(dd+" 23:59:59",Format24Datetime);
	}

	/**
	 * 获取当前时间到今天的午夜
	 *
	 * @return
	 */
	public static String getTodayTillMiddleNight() {
		return getToday(FormatDay1) + " 23:59:59";
	}

	public static java.sql.Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static java.util.Date getCurrentUtilDate() {
		return new java.util.Date(System.currentTimeMillis());
	}

	public static String getToday(String formatDay) {
		java.util.Date today = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat(formatDay);
		return df.format(today).toString();
	}

	public static String get24DateTime() {
		return get24DateTime(Format24Datetime);
	}

	public static String get24DateTime(String format24DateTime) {
		java.util.Date today = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat(format24DateTime);
		return df.format(today).toString();
	}

	/**
	 * 获取当年的最后一天
	 * @return
	 */
	public static Date getLastDayOfCurrYear(){
		Calendar currCal=Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getLastDayOfYear(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * @return
	 */
	public static Date getLastDayOfYear(Date date){
		String y = DateUtil.getFormatDate(date,"yyyy");
		int currentYear = Integer.valueOf(y);
		return getLastDayOfYear(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * @param date 日期
	 * @return Date
	 */
	public static Date getYearFirst(Date date){
		Calendar currCal=Calendar.getInstance();
		currCal.setTime(date);
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getLastDayOfYear(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}

	/**
	 * 获取某月第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDayOfMonth = calendar.getTime();
		return firstDayOfMonth;
	}

	/**
	 * 获取某月最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 判断某一年有多少天
	 * @param year
	 * @return
	 */
	public static int getDays(int year){
		int days;//某年(year)的天数
		if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){//闰年的判断规则
			days=366;
		}else{
			days=365;
		}
		return days;
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 * */
	public static int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static double minutesToHours(Double minutes){
		if(minutes==null){
			return 0;
		}
		double l =minutes/60.0;
		BigDecimal b = new BigDecimal(l);
		double value  =  b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
		return value;
	}

	/**
	 * 为时间添加时分
	 * @param date
	 * @param ht，格式:08:30
	 * @return
	 */
	public static Date addHourMinToDate(Date date,String ht){
		if(date==null){
			date = new Date();
		}
		String ss = DateUtil.getFormatDate(date,DateUtil.FormatDay1);
		Date dd= DateUtil.getDate(ss+" "+ht+":00",DateUtil.Format24Datetime);
		return dd;
	}

	/**
	 * 判断两个日期是否同一天
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isTheSameDay(Date d1,Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
	}

}
