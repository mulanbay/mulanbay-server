package mulanbay;

/**
 * @author fenghong
 * @title: KeyValue
 * @description: TODO
 * @date 2019/4/19:11 AM
 */
public class KeyValue {

    private String id;

    private String text;

    public KeyValue() {
    }

    public KeyValue(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
