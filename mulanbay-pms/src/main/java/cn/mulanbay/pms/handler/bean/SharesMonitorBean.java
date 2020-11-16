package cn.mulanbay.pms.handler.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 股票监控
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class SharesMonitorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    //上次最新价格
    private double lastPrice;

    //跌的总次数
    private int fails = 0;

    //上涨的总次数
    private int gains = 0;

    //计数器
    private int count = 0;

    private Type type = Type.FAIL;

    //上一次获取时间
    private Date lastGetTime;

    //最低价
    private Double minPrice;

    //最高价
    private Double maxPrice;

    public void updateCurrentPrice(double currentPrice) {
        this.lastPrice = currentPrice;
        if (minPrice == null || minPrice > currentPrice) {
            minPrice = currentPrice;
        }
        if (maxPrice == null || maxPrice < currentPrice) {
            maxPrice = currentPrice;
        }
    }

    /**
     * 更新类型
     *
     * @param type
     */
    public void updateType(Type type) {
        if (this.type == type) {
            //和原来的一样
            count++;
        } else {
            //重新计算
            count = 1;
        }
        this.type = type;
        if (type == Type.FAIL) {
            fails++;
        } else {
            gains++;
        }
    }

    /**
     * 重设涨跌的次数
     */
    public void resetGF() {
        fails = 0;
        gains = 0;
    }


    public void addCount() {
        count++;
    }

    public void resetCount() {
        count = 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getLastGetTime() {
        return lastGetTime;
    }

    public void setLastGetTime(Date lastGetTime) {
        this.lastGetTime = lastGetTime;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getFails() {
        return fails;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }

    public int getGains() {
        return gains;
    }

    public void setGains(int gains) {
        this.gains = gains;
    }

    public enum Type {
        GAIN("涨"), FAIL("跌");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
