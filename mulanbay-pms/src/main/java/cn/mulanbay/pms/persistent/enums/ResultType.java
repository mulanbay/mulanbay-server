package cn.mulanbay.pms.persistent.enums;

/**
 * 提醒值返回类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum ResultType {
    DATE(0, "日期"),
    NUMBER(1, "数字"),
    NAME_DATE(2, "名称-日期"),
    NAME_NUMBER(3, "名称-数字");
    private int value;
    private String name;

    ResultType(int value, String name) {
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

