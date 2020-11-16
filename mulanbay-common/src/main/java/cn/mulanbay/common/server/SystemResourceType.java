package cn.mulanbay.common.server;

/**
 * 系统资源类型
 *
 * @author fenghong
 * @create 2020-09-15 22:00
 */
public enum SystemResourceType {

    DISK(0, "磁盘"), MEMORY(1, "内存"), CPU(2, "CPU");

    private int value;

    private String name;

    SystemResourceType(int value, String name) {
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
