package cn.mulanbay.pms.persistent.enums;

/**
 * 比对类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum CompareType {

    MORE(0, "大于"), LESS(1, "小于");

    private int value;

    private String name;

    CompareType(int value, String name) {
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
