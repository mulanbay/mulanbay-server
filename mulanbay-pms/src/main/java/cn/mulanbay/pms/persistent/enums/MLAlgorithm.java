package cn.mulanbay.pms.persistent.enums;

/**
 * 机器学习算法
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum MLAlgorithm {

    DTC(0, "决策树"),
    DTR(1, "决策树回归"),
    RTC(2, "随机森林"),
    RTR(3, "随机森林回归"),
    LR(4, "线性回归"),
    XGBR(5, "XGBoost回归");

    private int value;

    private String name;

    MLAlgorithm(int value, String name) {
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
