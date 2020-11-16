package cn.mulanbay.pms.persistent.enums;

/**
 * 消费记录类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum GoodsConsumeType {

    NORMAL(0, "普通消费"), OUTBURST(1, "突发消费");

    private int value;

    private String name;

    GoodsConsumeType(int value, String name) {
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
