package cn.mulanbay.pms.persistent.enums;

/**
 * 枚举字段值类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum EnumIdType {

    //枚举的字段名
    FIELD(0, "枚举的字段名"),
    //枚举的序列
    ORDINAL(1, "枚举的序列"),
    //自定义的value值
    VALUE(2, "自定义的value值");

    private int value;

    private String name;

    EnumIdType(int value, String name) {
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

    public static EnumIdType getEnumIdType(int value) {
        for (EnumIdType bt : EnumIdType.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
