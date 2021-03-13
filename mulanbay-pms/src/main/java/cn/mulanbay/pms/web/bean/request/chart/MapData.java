package cn.mulanbay.pms.web.bean.request.chart;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/13
 */
public class MapData {

    private String name;
    private Object value;
    private Boolean selected;

    public MapData(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
