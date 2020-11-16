package cn.mulanbay.pms.persistent.enums;

/**
 * 消息发送状态
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum MessageSendStatus {

    UN_SEND(0, "未发送"), SEND_SUCCESS(1, "发送成功"), SEND_FAIL(2, "发送失败");

    private int value;

    private String name;

    MessageSendStatus(int value, String name) {
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
