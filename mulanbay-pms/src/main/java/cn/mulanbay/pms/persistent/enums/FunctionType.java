package cn.mulanbay.pms.persistent.enums;

/**
 * 功能操作类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum FunctionType {

    CREATE(0, "新增"), EDIT(1, "修改"), DELETE(2, "删除"), SEARCH(3, "查询"), STAT(4, "统计"), OTHER(5, "其他");

    private int value;

    private String name;

    FunctionType(int value, String name) {
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
