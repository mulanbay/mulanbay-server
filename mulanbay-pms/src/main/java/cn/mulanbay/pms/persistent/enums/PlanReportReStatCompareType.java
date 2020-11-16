package cn.mulanbay.pms.persistent.enums;

/**
 * 计划报告数据比较类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum PlanReportReStatCompareType {

    ORIGINAL(0, "原始数据"),//PlanReport表中已经写入的planCountValue，planValue
    ORIGINAL_LATEST(1, "当年最新数据"),//根据PlanReport表中已经写入的year去查询当年最新的数据
    LATEST(2, "最新"),//获取今年最新的
    SPECIFY(3, "指定年份");

    private int value;

    private String name;

    PlanReportReStatCompareType(int value, String name) {
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
