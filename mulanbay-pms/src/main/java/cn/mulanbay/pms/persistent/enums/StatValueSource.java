package cn.mulanbay.pms.persistent.enums;

/**
 * 来源
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum StatValueSource {

    SQL(0, "数据库查询"),
    ENUM(1, "枚举类"),
    DATA_DICT(2, "数据字典"),
    JSON(3, "Json数据");

    private int value;

    private String name;

    StatValueSource(int value, String name) {
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

    public static StatValueSource getAccountType(int value) {
        for (StatValueSource bt : StatValueSource.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
