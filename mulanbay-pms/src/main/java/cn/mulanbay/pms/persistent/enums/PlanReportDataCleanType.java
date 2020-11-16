package cn.mulanbay.pms.persistent.enums;

/**
 * 计划报告数据清理类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum PlanReportDataCleanType {

    ALL(0, "所有"), BOTH_ZERO(1, "全部为零"), ONCE_ZERO(2, "其中有一个为零");

    private int value;

    private String name;

    PlanReportDataCleanType(int value, String name) {
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
