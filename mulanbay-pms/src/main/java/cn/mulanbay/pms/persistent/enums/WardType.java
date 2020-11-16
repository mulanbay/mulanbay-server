package cn.mulanbay.pms.persistent.enums;

/**
 * 前后类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum WardType {

    FORWARD(0, "向前"), BACKWARD(1, "向后");

    private int value;

    private String name;

    WardType(int value, String name) {
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
