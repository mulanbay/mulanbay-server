package cn.mulanbay.schedule.enums;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2017-10-19 13:51
 **/
public enum RedoType {

    CANNOT(0,"不能重做"),//调度执行失败后，不能被处重做
    MUNUAL_REDO(1,"手动重做"),//调度执行失败后，只能由人工进行重做
    AUTO_REDO(2,"自动重做"),//调度执行失败后，由自动重做JOB自动重做
    ALL_REDO(3,"手动、自动重做");//调度执行失败后，即可以手动重做，也可以由自动重做JOB自动重做

    private Integer value;

    private String name;

    RedoType(Integer value, String name) {
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
