package cn.mulanbay.pms.persistent.enums;

/**
 * 日历来源
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum UserCalendarSource {

    MANUAL(0, "手动"),
    NOTIFY(1, "提醒"),
    PLAN(2, "计划"),
    COMMON_RECORD(3, "通用"),
    BUDGET(4, "预算"),
    TREAT_OPERATION(5, "手术"),
    TREAT_DRUG(6, "用药"),
    BUY_RECORD(7, "消费");

    private int value;

    private String name;

    UserCalendarSource(int value, String name) {
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

    public static UserCalendarSource getUserCalendarSource(int value) {
        for (UserCalendarSource rs : UserCalendarSource.values()) {
            if (rs.getValue() == value) {
                return rs;
            }
        }
        return null;
    }
}
