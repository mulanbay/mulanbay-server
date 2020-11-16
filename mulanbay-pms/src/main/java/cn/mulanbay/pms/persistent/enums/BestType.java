package cn.mulanbay.pms.persistent.enums;

/**
 * 最佳类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BestType {

    ONCE(0, "曾经"), CURRENT(1, "当前");

    private int value;

    private String name;

    BestType(int value, String name) {
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
