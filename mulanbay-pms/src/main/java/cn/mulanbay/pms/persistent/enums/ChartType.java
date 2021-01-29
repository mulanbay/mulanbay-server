package cn.mulanbay.pms.persistent.enums;

/**
 * 图形类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum ChartType {

    PIE(0, "饼图"),
    LINE(1, "折线图"),
    BAR(2, "柱状图"),
    SHADOW(3, "矩阵图"),
    MAP(4, "地图"),
    RADAR(5, "雷达图"),
    SCATTER(6, "散点图"),
    TREE_MAP(7, "树形图"),
    CALENDER_PIE(8, "日历饼图"),
    MIX_LINE_BAR(9, "混合折线柱状图");
    private int value;

    private String name;

    ChartType(int value, String name) {
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
