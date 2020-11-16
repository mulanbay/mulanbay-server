package cn.mulanbay.pms.persistent.enums;

/**
 * 预算统计类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BudgetStatType {

    NAME(0, "名称"), TYPE(1, "类型"), PERIOD(2, "周期");

    private int value;

    private String name;

    BudgetStatType(int value, String name) {
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
