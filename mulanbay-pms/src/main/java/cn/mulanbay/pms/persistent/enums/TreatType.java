package cn.mulanbay.pms.persistent.enums;

/**
 * 看病类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum TreatType {

    TREAT(0, "看病"),
    BUY_DRUG(1, "买药"),
    CHECK_UP(2, "体检"),
    TEETH(3, "看牙"),
    VACCINATE(3, "疫苗接种");

    private int value;

    private String name;

    TreatType(int value, String name) {
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
