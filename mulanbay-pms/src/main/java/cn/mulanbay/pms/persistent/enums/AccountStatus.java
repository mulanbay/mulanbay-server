package cn.mulanbay.pms.persistent.enums;

/**
 * 账户状态
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum AccountStatus {

    IMMOVABLE(0, "不可动资产"), MOVABLE(1, "可动资产");

    private int value;

    private String name;

    AccountStatus(int value, String name) {
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

    public static AccountStatus getAccountStatus(int value) {
        for (AccountStatus bt : AccountStatus.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
