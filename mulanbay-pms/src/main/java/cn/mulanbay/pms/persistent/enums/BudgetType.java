package cn.mulanbay.pms.persistent.enums;

/**
 * 预算类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BudgetType {

    INSURANCE(0, "保险"), STUDY(1, "学习培训"), FINANCE(2, "理财产品"), LIVE(3, "生活类"), OTHER(4, "其他");

    private int value;

    private String name;

    BudgetType(int value, String name) {
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

    public static BudgetType getBudgetType(int value) {
        for (BudgetType bt : BudgetType.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
