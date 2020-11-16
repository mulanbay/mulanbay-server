package cn.mulanbay.pms.persistent.enums;


/**
 * 字段值类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum StatValueClass {

    LONG(0, "LONG"), INTEGER(1, "INTEGER"), SHORT(2, "SHORT"), STRING(3, "STRING");

    private int value;

    private String name;

    StatValueClass(int value, String name) {
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
