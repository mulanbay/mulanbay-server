package cn.mulanbay.pms.persistent.enums;

/**
 * 检查结果
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum TreatTestResult {

    LOWER(0, "过低"),
    NORMAL(1, "正常"),
    HIGHER(2, "过高"),
    DISEASE(3, "确诊疾病");

    private int value;

    private String name;

    TreatTestResult(int value, String name) {
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
