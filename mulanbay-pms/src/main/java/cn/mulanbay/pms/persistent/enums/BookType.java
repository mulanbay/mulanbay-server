package cn.mulanbay.pms.persistent.enums;

/**
 * 书籍类型
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BookType {

    PAPER(0, "纸质书"),
    EBOOK(1, "电子书");
    private int value;
    private String name;

    BookType(int value, String name) {
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

    public static BookType getBookType(int ordinal) {
        for (BookType bookType : BookType.values()) {
            if (bookType.ordinal() == ordinal) {
                return bookType;
            }
        }
        return null;
    }
}
