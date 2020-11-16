package cn.mulanbay.schedule.enums;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2017-10-19 13:51
 **/
public enum TaskUniqueType{

    IDENTITY(0,"唯一标识"),
    BUSS_DATE(1,"运营日期");

    private Integer value;

    private String name;

    TaskUniqueType(Integer value, String name) {
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
