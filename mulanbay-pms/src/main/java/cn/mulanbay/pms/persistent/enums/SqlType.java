package cn.mulanbay.pms.persistent.enums;


/**
 * 数据库查询语句类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum SqlType {
    SQL(0, "sql"),
    HQL(1, "hql");
    private int value;
    private String name;

    SqlType(int value, String name) {
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
