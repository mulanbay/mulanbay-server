package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DietTimeStat {

    private Object xValue;

    /**
     * 时间点
     */
    private Object yValue;

    public Object getxValue() {
        return xValue;
    }

    public void setxValue(Object xValue) {
        this.xValue = xValue;
    }

    public Object getyValue() {
        return yValue;
    }

    public void setyValue(Object yValue) {
        this.yValue = yValue;
    }

    public double getxDoubleValue() {
        if (xValue instanceof BigDecimal) {
            return ((BigDecimal) xValue).doubleValue();
        }
        if (xValue instanceof BigInteger) {
            return ((BigInteger) xValue).doubleValue();
        }
        return Double.valueOf(xValue.toString());
    }

    public double getyDoubleValue() {
        if (yValue == null) {
            return 0;
        }
        if (yValue instanceof BigDecimal) {
            return ((BigDecimal) yValue).doubleValue();
        }
        if (yValue instanceof BigInteger) {
            return ((BigInteger) yValue).doubleValue();
        }
        return Double.valueOf(yValue.toString());
    }

}
