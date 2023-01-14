package cn.mulanbay.pms.persistent.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BuyRecordRadarStat implements DateStat {

    // 商品类型，来源类型等
    private Object groupId;

    // 可以为天(20100101),周(1-36),月(1-12),年(2014)
    private Number indexValue;

    private BigInteger totalCount;

    // 价格，精确到元，可以为运费、总价
    private BigDecimal totalPrice;

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    @Override
    public Integer getDateIndexValue() {
        return indexValue==null ? null : indexValue.intValue();
    }

    public Number getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Number indexValue) {
        this.indexValue = indexValue;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 获取groupId值，因为不同的类型映射不一样，在这里统一处理
     *
     * @return
     */
    public int getIntGroupIdValue() {
        if (groupId == null) {
            //未知
            return -1;
        } else if (groupId instanceof BigInteger) {
            BigInteger bi = (BigInteger) groupId;
            return bi.intValue();
        } else if (groupId instanceof BigDecimal) {
            BigDecimal bi = (BigDecimal) groupId;
            return bi.intValue();
        } else {
            return Integer.valueOf(groupId.toString());
        }
    }

}
