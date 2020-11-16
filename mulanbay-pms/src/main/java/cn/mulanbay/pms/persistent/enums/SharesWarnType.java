package cn.mulanbay.pms.persistent.enums;

/**
 * 股票警告类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum SharesWarnType {

    RC_MIN_PRICE(0, "股票达到止损价格"),
    RC_MAX_PRICE(1, "股票达到抛售价格"),
    CT_FAIL(2, "股票连续下跌"),
    CT_GAIN(3, "股票连续上涨"),
    LOW_SCORE(4, "股票评分过低"),
    HIGH_SCORE(5, "股票评分过高"),
    LOW_TURN_OVER(6, "换手率过低"),
    HIGH_TURN_OVER(7, "换手率过高"),
    TIMELY_STAT(8, "股票定时统计", false),
    INDEX_POINT_LOW(9, "大盘指数变低", false),
    INDEX_POINT_HIGH(10, "大盘指数变高", false);


    private int value;

    private String name;

    /**
     * 是否保存记录
     */
    private boolean save = true;

    SharesWarnType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    SharesWarnType(int value, String name, boolean save) {
        this.value = value;
        this.name = name;
        this.save = save;
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

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public static SharesWarnType getSharesWarnType(int value) {
        for (SharesWarnType bt : SharesWarnType.values()) {
            if (bt.getValue() == value) {
                return bt;
            }
        }
        return null;
    }
}
