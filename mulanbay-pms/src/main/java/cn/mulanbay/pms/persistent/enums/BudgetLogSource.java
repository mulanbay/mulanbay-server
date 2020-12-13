package cn.mulanbay.pms.persistent.enums;

/**
 * 预算日志的来源
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BudgetLogSource {

    MANUAL(0, "手动"),
    AUTO(1, "自动"),
    REAL_TIME(2, "实时");

    private int value;

    private String name;

    BudgetLogSource(int value, String name) {
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
