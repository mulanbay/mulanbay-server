package cn.mulanbay.pms.persistent.enums;

/**
 * 系统消息监控业务
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum MonitorBussType {

    ALL(0, "全部"),
    SCHEDULE(1, "调度"),
    ERROR_CODE(2, "错误代码"),
    SECURITY(3, "安全"),
    SYSTEM(4, "系统"),
    COMMON(5, "通用"),
    STAT(6, "统计");

    private int value;

    private String name;

    MonitorBussType(int value, String name) {
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

    public static MonitorBussType getMonitorBussType(int value) {
        for (MonitorBussType rs : MonitorBussType.values()) {
            if (rs.getValue() == value) {
                return rs;
            }
        }
        return null;
    }
}
