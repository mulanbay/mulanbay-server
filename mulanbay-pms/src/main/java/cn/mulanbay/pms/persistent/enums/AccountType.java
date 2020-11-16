package cn.mulanbay.pms.persistent.enums;

/**
 * 账户类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum AccountType {

    BANK(0, "银行账户"), FINANCE(1, "理财产品"), LOAN(2, "借款"), OTHER(3, "其他");

    private int value;

    private String name;

    AccountType(int value, String name) {
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

    public static AccountType getAccountType(int value) {
        for (AccountType bt : AccountType.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
