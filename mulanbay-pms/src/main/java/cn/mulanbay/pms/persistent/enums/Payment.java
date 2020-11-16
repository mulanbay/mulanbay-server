package cn.mulanbay.pms.persistent.enums;

/**
 * 支付方式
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum Payment {
    ALIPAY(0, "支付宝"), WECHAT(1, "微信支付"), UNIONPAY(2, "银联"), CASH(3, "现金"), OTHER(4, "其他");
    private int value;

    private String name;

    Payment(int value, String name) {
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

    public static Payment getPayment(int ordinal) {
        for (Payment payment : Payment.values()) {
            if (payment.ordinal() == ordinal) {
                return payment;
            }
        }
        return null;
    }
}
