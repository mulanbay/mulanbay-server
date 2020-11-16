package cn.mulanbay.pms.persistent.enums;

/**
 * 人生经历类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum ExperienceType {

    LIVE(0, "生活"), WORK(1, "工作"), TRAVEL(2, "旅行"), STUDY(3, "读书");
    private int value;

    private String name;

    ExperienceType(int value, String name) {
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

    public static ExperienceType getExperienceType(int value) {
        for (ExperienceType bt : ExperienceType.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
