package cn.mulanbay.pms.persistent.enums;

/**
 * 书籍语言
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public enum BookLanguage {

    CHINESE(0, "中文"),
    ENGLISH(1, "英文");
    private int value;
    private String name;

    BookLanguage(int value, String name) {
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

    public static BookLanguage getLanguage(int ordinal) {
        for (BookLanguage language : BookLanguage.values()) {
            if (language.ordinal() == ordinal) {
                return language;
            }
        }
        return null;
    }
}
