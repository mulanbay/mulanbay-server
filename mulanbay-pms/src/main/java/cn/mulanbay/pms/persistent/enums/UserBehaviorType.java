package cn.mulanbay.pms.persistent.enums;

/**
 * 行为类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum UserBehaviorType {

    MUSIC(0, "音乐"),
    SPORT(1, "运动"),
    BUY(2, "消费"),
    HEALTH(3, "健康"),
    READ(4, "阅读"),
    LIFE(5, "人生经历"),
    LOG(6, "日志"),
    WORK(7, "工作"),
    COMMON(8, "通用"),
    DIET(9, "饮食");

    private int value;

    private String name;

    UserBehaviorType(int value, String name) {
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

    public static UserBehaviorType getUserBehaviorType(String name) {
        for (UserBehaviorType bt : UserBehaviorType.values()) {
            if (bt.name.equals(name)) {
                return bt;
            }
        }
        return null;
    }
}
