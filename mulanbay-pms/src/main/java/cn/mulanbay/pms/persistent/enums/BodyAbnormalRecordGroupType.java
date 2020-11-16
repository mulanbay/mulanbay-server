package cn.mulanbay.pms.persistent.enums;

/**
 * 身体不适分组类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BodyAbnormalRecordGroupType {

    ORGAN("organ", 0, "器官"),
    DISEASE("disease", 1, "疾病"),
    PAINLEVEL("pain_level", 1, "疼痛级别"),
    IMPORTANTLEVEL("important_level", 1, "重要等级"),
    LASTDAYS("last_days", 1, "持续天数");

    private String field;

    private int value;

    private String name;

    BodyAbnormalRecordGroupType(String field) {
        this.field = field;
    }

    BodyAbnormalRecordGroupType(String field, int value, String name) {
        this.field = field;
        this.value = value;
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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
