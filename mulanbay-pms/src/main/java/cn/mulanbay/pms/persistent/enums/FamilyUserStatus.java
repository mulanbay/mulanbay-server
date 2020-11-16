package cn.mulanbay.pms.persistent.enums;

/**
 * 家庭用户加入状态
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum FamilyUserStatus {

    APPLYING(0, "邀请加入中"),
    PASSED(1, "已通过"),
    SEP_APPLYING(2, "脱离申请中");

    private int value;

    private String name;

    FamilyUserStatus(int value, String name) {
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
