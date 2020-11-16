package cn.mulanbay.pms.persistent.enums;

/**
 * QA的消息来源
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum QaMessageSource {

    PC(0, "电脑端"),
    MOBILE(1, "移动端"),
    WECHAT(2, "微信公众号");

    private int value;

    private String name;

    QaMessageSource(int value, String name) {
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

    public static QaMessageSource getAccountType(int value) {
        for (QaMessageSource bt : QaMessageSource.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
