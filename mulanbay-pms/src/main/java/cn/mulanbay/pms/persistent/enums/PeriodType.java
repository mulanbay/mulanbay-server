package cn.mulanbay.pms.persistent.enums;

/**
 * 周期类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum PeriodType {

    ONCE(0, "单次"),
    DAILY(1, "每天"),
    WEEKLY(2, "每周"),
    MONTHLY(3, "每月"),
    QUARTERLY(4, "每季度"),
    YEARLY(5, "每年");

    private int value;

    private String name;

    PeriodType(int value, String name) {
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

    public static PeriodType getPeriodType(int value) {
        for (PeriodType bt : PeriodType.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
