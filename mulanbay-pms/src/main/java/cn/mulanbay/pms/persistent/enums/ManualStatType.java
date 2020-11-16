package cn.mulanbay.pms.persistent.enums;

/**
 * 手动统计类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum ManualStatType {

    RE_STAT(0, "重新统计"), STAT_MISS(1, "统计遗漏");

    private int value;

    private String name;

    ManualStatType(int value, String name) {
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
