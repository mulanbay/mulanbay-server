package cn.mulanbay.pms.persistent.enums;

/**
 * 门诊类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum TreatStage {

    FIRST(0, "首诊"),
    RETURN(1, "复诊");

    private int value;

    private String name;

    TreatStage(int value, String name) {
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
