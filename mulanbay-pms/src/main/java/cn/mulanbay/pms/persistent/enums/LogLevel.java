package cn.mulanbay.pms.persistent.enums;

/**
 * 日志级别
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum LogLevel {

    NORMAL(0, "普通"), WARNING(1, "警告"), ERROR(2, "异常"), FATAL(3, "致命");

    private int value;

    private String name;

    LogLevel(int value, String name) {
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

    public static LogLevel getLogLevel(int level) {
        for (LogLevel bt : LogLevel.values()) {
            if (bt.value == level) {
                return bt;
            }
        }
        return null;
    }
}
