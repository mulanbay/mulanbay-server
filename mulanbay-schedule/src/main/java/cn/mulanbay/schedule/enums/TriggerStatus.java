package cn.mulanbay.schedule.enums;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2017-10-19 13:54
 **/
public enum TriggerStatus  {
    DISABLE(0,"未启用"),
    ENABLE(1,"启用");

    private Integer value;

    private String name;

    TriggerStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
