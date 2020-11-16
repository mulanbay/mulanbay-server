package cn.mulanbay.pms.persistent.enums;

/**
 * QA值返回类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum QaResultType {

    DIRECT(0, "直接回复"),
    REFER(1, "跳转回复"),
    SQL(2, "数据库查询"),
    CODE(3, "处理代码");

    private int value;

    private String name;

    QaResultType(int value, String name) {
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
