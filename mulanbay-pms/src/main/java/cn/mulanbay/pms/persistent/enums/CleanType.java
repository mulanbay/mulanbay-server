package cn.mulanbay.pms.persistent.enums;

/**
 * 清理类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum CleanType {

    DATE_COMPARE(0, "按时间条件"), TRUNCATE(1, "全表删除");

    private int value;

    private String name;

    CleanType(int value, String name) {
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
