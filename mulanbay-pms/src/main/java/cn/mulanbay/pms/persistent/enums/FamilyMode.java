package cn.mulanbay.pms.persistent.enums;

/**
 * 家庭登录模式
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum FamilyMode {

    P(0, "个人"),
    F(1, "家庭");

    private int value;

    private String name;

    FamilyMode(int value, String name) {
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
