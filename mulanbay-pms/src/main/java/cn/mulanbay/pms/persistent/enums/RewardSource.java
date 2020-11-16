package cn.mulanbay.pms.persistent.enums;

/**
 * 积分奖励来源
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum RewardSource {

    OPERATION(0, "操作"),
    NOTIFY(1, "提醒"),
    PLAN(2, "计划"),
    COMMON_RECORD(3, "通用记录"),
    DREAM(4, "梦想"),
    BUDGET_LOG(5, "预算日志"),
    BUDGET(6, "预算"),
    MANUAL(7, "人工修正");

    private int value;

    private String name;

    RewardSource(int value, String name) {
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

    public static RewardSource getRewardSource(int value) {
        for (RewardSource rs : RewardSource.values()) {
            if (rs.getValue() == value) {
                return rs;
            }
        }
        return null;
    }
}
