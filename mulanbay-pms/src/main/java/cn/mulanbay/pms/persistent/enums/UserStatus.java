package cn.mulanbay.pms.persistent.enums;

/**
 * 用户状态
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum UserStatus {

    ENABLE(0, "启用"), DISABLE(1, "停用");
    private int value;

    private String name;

    UserStatus(int value, String name) {
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

    public static UserStatus getUserStatus(int ordinal) {
        for (UserStatus ds : UserStatus.values()) {
            if (ds.ordinal() == ordinal) {
                return ds;
            }
        }
        return null;
    }
}
