package cn.mulanbay.pms.persistent.enums;

/**
 * 预算的资金类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BudgetFeeType {

    BUY_RECORD(0, "消费"),
    TREAT_RECORD(1, "看病");

    private int value;

    private String name;

    BudgetFeeType(int value, String name) {
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
