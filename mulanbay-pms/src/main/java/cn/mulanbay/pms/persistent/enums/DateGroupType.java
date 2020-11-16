package cn.mulanbay.pms.persistent.enums;

/**
 * 时间分组类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum DateGroupType {
    MINUTE("分钟"),
    HOUR("点"),
    HOURMINUTE("时分"),
    DAY("天"),
    WEEK("周"),
    MONTH("月"),
    YEAR("年"),
    DAYOFMONTH("号"),
    DAYOFWEEK("(周号)"),
    YEARMONTH("年月"),
    DAYCALENDAR("日历"),
    TIMELINE("时间线");

    private String name;

    DateGroupType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static DateGroupType getDateGroupType(String s) {
        for (DateGroupType dg : DateGroupType.values()) {
            if (dg.name().equals(s)) {
                return dg;
            }
        }
        return null;
    }
}
