package cn.mulanbay.pms.persistent.enums;

/**
 * URL类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum UrlType {

    NORMAL(0, "普通"),
    REST_FULL(1, "RestFull");

    private int value;

    private String name;

    UrlType(int value, String name) {
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
