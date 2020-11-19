package cn.mulanbay.pms.persistent.enums;

/**
 * 地图类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum MapType {

    CHINA(0, "中国"),
    WORLD(1, "世界"),
    LOCATION(2, "地点(中国)"),
    TRANSFER_DOUBLE(3, "双向地图"),
    TRANSFER_SINGLE(4, "单向向地图"),
    LC_NAME(5, "经历(中国)");

    private int value;

    private String name;

    MapType(int value, String name) {
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
