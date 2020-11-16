package cn.mulanbay.pms.persistent.enums;

/**
 * 账号调整类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum AccountAdjustType {

    MANUAL(0, "人工修正"),
    AUTO(1, "自动"),
    LOAN(2, "借款"),
    SNAPSHOT(3, "快照"),
    OTHER(4, "其他");

    private int value;

    private String name;

    AccountAdjustType(int value, String name) {
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
