package cn.mulanbay.schedule.enums;

/**
 * 调度执行结果
 *
 * @author fenghong
 * @create 2017-10-19 22:56
 **/
public enum JobExecuteResult {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    SKIP(2,"跳过"),
    DUPLICATE(3,"重复");

    private Integer value;

    private String name;

    JobExecuteResult(Integer value, String name) {
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
