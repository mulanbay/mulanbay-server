package cn.mulanbay.pms.web.bean.response.chart;

/**
 * Y轴临时索引封装
 *
 * @author: fenghong
 * @date: 2022-08-14 15:35
 */
public class OverallYIndex {

    private String id;

    private String name;

    private String unit;

    private int index;

    public OverallYIndex() {
    }

    public OverallYIndex(String id, String name, String unit, int index) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
