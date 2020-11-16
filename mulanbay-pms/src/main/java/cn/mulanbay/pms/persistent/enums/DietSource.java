package cn.mulanbay.pms.persistent.enums;


/**
 * 食物来源
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum DietSource {

    SELF_MADE(0, "自己做"),
    RESTAURANT(1, "餐馆"),
    TAKE_OUT(2, "外卖"),
    MARKET(3, "超市"),
    OTHER(4, "其他");

    private int value;

    private String name;

    DietSource(int value, String name) {
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

    public static DietSource getDietSource(int value) {
        for (DietSource bt : DietSource.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
