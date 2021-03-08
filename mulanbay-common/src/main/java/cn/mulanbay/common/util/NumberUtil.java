package cn.mulanbay.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数字工具类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class NumberUtil {

	/**
	 * 产生随机数
	 * @param n 位数
	 * @return
	 */
	public static String getRandNum(int n) {
		StringBuffer numStr = new StringBuffer();
		int num;
		for (int i = 0; i < n; i++) {
			// Math.random() 随机出0-1之间的实数，返回值是一个double 类型的
			num = (int) (Math.random() * 10);
			numStr.append(String.valueOf(num));
		}
		return numStr.toString();
	}

	public static long getLongValue(Object o) {
		if (o == null) {
			return 0L;
		} else {
			return Long.valueOf(o.toString());
		}
	}

	public static long getLongValueFromFloat(Object o) {
		if (o == null) {
			return 0L;
		} else {
			return Float.valueOf(o.toString()).longValue();
		}
	}

	public static Long[] stringArrayToLongArray(String[] ss){
		Long[] result = new Long[ss.length];
		for(int i=0; i< ss.length;i++){
			result[i]=Long.valueOf(ss[i]);
		}
		return result;
	}

	public static Integer[] stringArrayToIntegerArray(String[] ss){
		Integer[] result = new Integer[ss.length];
		for(int i=0; i< ss.length;i++){
			result[i]=Integer.valueOf(ss[i]);
		}
		return result;
	}

	/**
	 * 获取平均值
	 * @param value
	 * @param counts
	 * @param scale
	 * @return
	 */
	public static double getAverageValue(BigDecimal value,BigInteger counts,int scale){
		if(value==null){
			return 0;
		}
		return getAverageValue(value.doubleValue(),counts.intValue(),scale);
	}

	/**
	 * 获取平均值
	 * @param value
	 * @param counts
	 * @param scale
	 * @return
	 */
	public static double getAverageValue(double value,int counts,int scale){
		if(counts==0){
			return 0;
		}
		double l =value/counts;
		BigDecimal b = new BigDecimal(l);
		double v  =  b.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
		return v;
	}

	/**
	 * 获取值保留小数位数
	 * @param value
	 * @param scale
	 * @return
	 */
	public static double getDoubleValue(double value,int scale){
		BigDecimal b = new BigDecimal(value);
		double v  =  b.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
		return v;
	}

	/**
	 * 获取百分数（乘了100的数）
	 * @param value
	 * @param total 总数
	 * @param scale
	 * @return
	 */
	public static double getPercentValue(long value,long total,int scale){
		if(total==0){
			return 0;
		}
		double l =(value*100.0)/total;
		BigDecimal b = new BigDecimal(l);
		double v  =  b.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
		return v;
	}

	/**
	 * 获取百分数（乘了100的数）
	 * @param value
	 * @param counts
	 * @param scale
	 * @return
	 */
	public static double getPercentValue(Double value,Double counts,int scale){
		if(value==null||counts==null||counts<=0){
			return 0;
		}
		double l =(value*100)/counts;
		BigDecimal b = new BigDecimal(l);
		double v  =  b.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
		return v;
	}

	/**
	 * 判断是否为数字格式或者小数
	 * @param str
	 *     待校验参数
	 * @return
	 *     如果全为数字，返回true；否则，返回false
	 */
	public static boolean isNumber(String str){
		String reg = "\\d+(\\.\\d+)?";
		return str.matches(reg);
	}
}
