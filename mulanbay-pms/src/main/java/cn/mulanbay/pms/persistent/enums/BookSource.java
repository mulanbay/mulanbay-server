package cn.mulanbay.pms.persistent.enums;

/**
 * 图书来源
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BookSource {

    BUY(0, "购买"),
    BORROW(1, "借书"),
    OTHER(2, "其他");
    private int value;
    private String name;

    BookSource(int value, String name) {
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

    public static BookSource getBookSource(int ordinal) {
        for (BookSource ss : BookSource.values()) {
            if (ss.ordinal() == ordinal) {
                return ss;
            }
        }
        return null;
    }
}
