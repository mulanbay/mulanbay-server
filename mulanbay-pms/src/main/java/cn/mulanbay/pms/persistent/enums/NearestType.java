package cn.mulanbay.pms.persistent.enums;

/**
 * 最近类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum NearestType {

    MIN_TIME(0, "时间最早"),
    MAX_TIME(1, "时间最新"),
    MIN_VALUE(2, "数值最小"),
    MAX_VALUE(3, "数值最大");

    private int value;

    private String name;

    NearestType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
