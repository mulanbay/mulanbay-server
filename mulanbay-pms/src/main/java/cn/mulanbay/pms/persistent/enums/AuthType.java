package cn.mulanbay.pms.persistent.enums;

/**
 * 用户认证类型类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum AuthType {

    PASSWORD(0, "密码"),
    SMS(1, "短信"),
    EMAIL(2, "邮件"),
    WECHAT(3, "微信");

    private int value;

    private String name;

    AuthType(int value, String name) {
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
