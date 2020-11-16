package cn.mulanbay.pms.persistent.enums;

/**
 * 日历完成类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum UserCalendarFinishType {

    AUTO(0, "自动"), MANUAL(1, "手动"), EXPIRED(2, "过期");

    private int value;

    private String name;

    UserCalendarFinishType(int value, String name) {
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
