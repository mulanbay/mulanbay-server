package cn.mulanbay.pms.web.bean.request.userbehavior;

public class UserOperationWordCloudSearch extends UserOperationStatSearch {

    private String field;

    private int picWidth = 500;

    private int picHeight = 400;

    /**
     * 是否忽略短语
     */
    private Boolean ignoreShort;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public Boolean getIgnoreShort() {
        return ignoreShort;
    }

    public void setIgnoreShort(Boolean ignoreShort) {
        this.ignoreShort = ignoreShort;
    }
}
