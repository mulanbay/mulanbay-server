package cn.mulanbay.business.handler;

/**
 * ${DESCRIPTION}
 * Handler详情明细
 *
 * @author fenghong
 * @create 2017-10-19 21:50
 **/
public class HandlerInfoDetail {

    private String key;

    private String value;

    public HandlerInfoDetail() {
    }

    public HandlerInfoDetail(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
