package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

public class SystemLogAnalyseStat {

    private Object id;
    private String name;
    private BigInteger totalCount;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }
}
