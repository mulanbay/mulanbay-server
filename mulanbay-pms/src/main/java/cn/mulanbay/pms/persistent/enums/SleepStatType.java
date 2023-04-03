package cn.mulanbay.pms.persistent.enums;


/**
 * 睡眠字段统计类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum SleepStatType {

    DURATION("睡眠时长", "小时", 0),
    SLEEP_TIME("睡眠点", "点", 1),
    GETUP_TIME("起床点", "点", 2),
    QUALITY("睡眠质量", "分", 3),
    WAKEUP_COUNT("醒来次数", "次", 4);

    private String name;

    private String unit;

    private int value;

    SleepStatType(String name) {
        this.name = name;
    }

    SleepStatType(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    SleepStatType(String name, String unit, int value) {
        this.name = name;
        this.unit = unit;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
