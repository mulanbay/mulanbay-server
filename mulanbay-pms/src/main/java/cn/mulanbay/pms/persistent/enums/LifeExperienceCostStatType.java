package cn.mulanbay.pms.persistent.enums;

/**
 * 人生经历花费统计类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum LifeExperienceCostStatType {

    CONSUME_TYPE("消费类型", 0),
    TYPE("经历类型", 1),
    LE("人生经历", 2);

    private String name;

    private int value;

    LifeExperienceCostStatType(String name) {
        this.name = name;
    }


    LifeExperienceCostStatType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
