package cn.mulanbay.pms.persistent.enums;

/**
 * 曲子类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum MusicPracticeTuneType {

    TUNE(0, "曲子"), TECH(1, "技术");

    private int value;

    private String name;

    MusicPracticeTuneType(int value, String name) {
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
