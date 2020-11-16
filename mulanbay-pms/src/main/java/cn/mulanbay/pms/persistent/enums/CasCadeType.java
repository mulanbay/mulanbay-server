package cn.mulanbay.pms.persistent.enums;

/**
 * 级联类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum CasCadeType {

    NOT_CASCADE(0, "不级联"), CASCADE_NEXT(1, "级联下一层"), BE_CASCADED(2, "级联上一层");

    private int value;

    private String name;

    CasCadeType(int value, String name) {
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
