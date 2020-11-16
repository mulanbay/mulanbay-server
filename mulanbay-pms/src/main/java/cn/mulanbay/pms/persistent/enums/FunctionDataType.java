package cn.mulanbay.pms.persistent.enums;

/**
 * 功能点类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum FunctionDataType {

    C(0, "菜单"),
    F(1, "按钮"),
    D(2, "条件"),
    M(3, "目录");

    private int value;

    private String name;

    FunctionDataType(int value, String name) {
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
