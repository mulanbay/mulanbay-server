package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: TODO(一句话描述该类的功能)
 * @Author: fenghong
 * @Create : 2020/12/13 16:38
 */
public class BuyRecordBudgetStat {

    private BigDecimal totalPrice;

    private Date maxBuyDate;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getMaxBuyDate() {
        return maxBuyDate;
    }

    public void setMaxBuyDate(Date maxBuyDate) {
        this.maxBuyDate = maxBuyDate;
    }
}
