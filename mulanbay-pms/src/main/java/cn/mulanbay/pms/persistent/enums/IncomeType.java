package cn.mulanbay.pms.persistent.enums;

/**
 * 收入类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum IncomeType {

    SALARY(0, "工资"),
    FINANCE(1, "理财"),
    PART_TIME(2, "兼职"),
    SECONDHAND_SOLD(3, "二手出售"),
    REFUND(4, "退款"),
    OTHER(5, "其他");

    private int value;

    private String name;

    IncomeType(int value, String name) {
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

    public static IncomeType getIncomeType(int value) {
        for (IncomeType bt : IncomeType.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
