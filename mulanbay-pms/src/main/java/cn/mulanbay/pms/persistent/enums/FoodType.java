package cn.mulanbay.pms.persistent.enums;

/**
 * 食物类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum FoodType {

    RICE(0, "米饭"), NOODLE(1, "面食"), OTHER(2, "其他"), FRUIT(3, "水果"), SNACKS(4, "零食"), TEA(5, "茶");

    private int value;

    private String name;

    FoodType(int value, String name) {
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

    public static FoodType getFoodType(int value) {
        for (FoodType bt : FoodType.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
