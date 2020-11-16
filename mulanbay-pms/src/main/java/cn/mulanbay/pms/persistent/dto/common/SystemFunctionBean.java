package cn.mulanbay.pms.persistent.dto.common;

import java.math.BigInteger;

/**
 * 系统功能点封装
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class SystemFunctionBean {

    private BigInteger id;

    private BigInteger pid;

    private String name;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getPid() {
        return pid;
    }

    public void setPid(BigInteger pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
