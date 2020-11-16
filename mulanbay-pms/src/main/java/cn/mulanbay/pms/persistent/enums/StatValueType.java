package cn.mulanbay.pms.persistent.enums;

/**
 * 配置值类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum StatValueType {

    NOTIFY(0, "提醒"),
    REPORT(1, "报表"),
    PLAN(2, "计划"),
    BEHAVIOR(3, "用户行为"),
    CHART(4, "图表"),
    CALENDAR(5, "日历");

    private int value;

    private String name;

    StatValueType(int value, String name) {
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
