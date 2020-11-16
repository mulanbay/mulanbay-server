package cn.mulanbay.pms.persistent.enums;

/**
 * 消息类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum MessageType {

    MAIL(0, "邮件"), SMS(1, "短信"), WX(2, "微信");

    private int value;

    private String name;

    MessageType(int value, String name) {
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
