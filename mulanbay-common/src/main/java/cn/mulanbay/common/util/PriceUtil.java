package cn.mulanbay.common.util;

import java.math.BigDecimal;

/**
 * 价格操作工具类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class PriceUtil {

    public final static BigDecimal ZERO = new BigDecimal(0);

    /**
     * 价格是否相等
     * @param a
     * @param b
     * @return
     */
    public static boolean priceEquals(double a, double b){
        if(Math.abs(a-b)<=0.001){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param a
     * @param b
     * @return 1: a>b 0:a=b,-1:a<b
     */
    public static int compareTo(BigDecimal a, BigDecimal b){
        return a.compareTo(b);
    }


    public static boolean priceEquals(BigDecimal a, BigDecimal b){
        return priceEquals(a.doubleValue(),b.doubleValue());
    }

    /**
     * 乘法
     * @param a
     * @param n
     * @return
     */
    public static BigDecimal mul(BigDecimal a, int n){
        return a.multiply(new BigDecimal(n));
    }

    /**
     * 合计
     * @param values
     * @return
     */
    public static double sum(double... values){
        BigDecimal total = new BigDecimal(0);
        for(double d : values){
            total.add(new BigDecimal(d));
        }
        return total.doubleValue();
    }

    /**
     * 价格转换为String
     * @param scale
     * @param price
     * @return
     */
    public static String changeToString(int scale,BigDecimal price){
        if(price==null){
            return "0";
        }
        return price.setScale(scale,BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 价格转换为String
     * @param scale
     * @param price
     * @return
     */
    public static String changeToString(int scale,double price){
        BigDecimal bd = new BigDecimal(price);
        return bd.setScale(scale,BigDecimal.ROUND_HALF_UP).toString();
    }
}
