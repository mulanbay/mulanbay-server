package cn.mulanbay.pms.persistent.enums;

/**
 * 梦想状态
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum DreamStatus {

    CREATED(0, "新创建"),
    STARTED(1, "进行中"),
    FINISHED(2, "已实现"),
    PAUSED(3, "暂停中"),
    GIVEDUP(4, "已放弃");
    private int value;

    private String name;

    DreamStatus(int value, String name) {
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

    public static DreamStatus getDreamStatus(int ordinal) {
        for (DreamStatus ds : DreamStatus.values()) {
            if (ds.ordinal() == ordinal) {
                return ds;
            }
        }
        return null;
    }
}
