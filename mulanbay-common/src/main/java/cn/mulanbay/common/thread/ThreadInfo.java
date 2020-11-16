package cn.mulanbay.common.thread;

/**
 * @author fenghong
 * @title: ThreadInfo
 * @description: 线程信息
 * @create 2018-01-20 21:44
 */
public class ThreadInfo {

    private String name;

    private String value;

    public ThreadInfo() {
    }

    public ThreadInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
