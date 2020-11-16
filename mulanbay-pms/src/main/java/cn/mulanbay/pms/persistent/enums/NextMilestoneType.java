package cn.mulanbay.pms.persistent.enums;

/**
 * 下一个运动里程碑类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum NextMilestoneType {

    CURRENT(0, "当前"), WHOLE(1, "全部");

    private int value;

    private String name;

    NextMilestoneType(int value, String name) {
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
