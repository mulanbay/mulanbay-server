package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/15
 */
public class NameCountDto {

    private String name;

    private BigInteger counts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getCounts() {
        return counts;
    }

    public void setCounts(BigInteger counts) {
        this.counts = counts;
    }
}
