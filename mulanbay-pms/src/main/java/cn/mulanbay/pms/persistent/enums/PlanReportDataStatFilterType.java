package cn.mulanbay.pms.persistent.enums;

/**
 * 计划报告数据过滤类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum PlanReportDataStatFilterType {

    ORIGINAL(0, "默认"), NO_USER(1, "不过滤用户"), NO_DATE(1, "不过滤时间"), NONE(1, "完全不过滤");

    private int value;

    private String name;

    PlanReportDataStatFilterType(int value, String name) {
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
