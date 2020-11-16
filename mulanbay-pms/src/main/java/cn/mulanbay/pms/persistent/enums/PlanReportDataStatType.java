package cn.mulanbay.pms.persistent.enums;

/**
 * 计划报告数据统计类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum PlanReportDataStatType {

    ORIGINAL(0, "原始数据"), PERCENT(1, "百分比");

    private int value;

    private String name;

    PlanReportDataStatType(int value, String name) {
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
