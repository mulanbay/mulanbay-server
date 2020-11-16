package cn.mulanbay.pms.persistent.enums;

/**
 * 账号分析类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum AccountAnalyseType {

    NAME(0, "名称"),
    TYPE(1, "类型"),
    STATUS(2, "状态");

    private int value;

    private String name;

    AccountAnalyseType(int value, String name) {
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
