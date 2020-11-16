package cn.mulanbay.pms.handler.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信模板消息封装
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class MessageContent {

    /**
     * 接收者openid
     */
    private String touser;
    /**
     * 模板ID
     */
    private String template_id;
    /**
     * 模板跳转链接
     */
    private String url;
    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private MiniprogramBean miniprogram;
    /**
     * 模板数据
     */
    private Map<String, MessageDataBean> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MiniprogramBean getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(MiniprogramBean miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, MessageDataBean> getData() {
        return data;
    }

    public void setData(Map<String, MessageDataBean> data) {
        this.data = data;
    }

    /**
     * 添加模板数据
     *
     * @param key
     * @param mdb
     */
    public void addMessageData(String key, MessageDataBean mdb) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, mdb);
    }

    /**
     * 添加模板数据
     *
     * @param key
     * @param value
     */
    public void addMessageData(String key, String value) {
        this.addMessageData(key, value, null);
    }

    /**
     * 添加模板数据
     *
     * @param key
     * @param value
     * @param color
     */
    public void addMessageData(String key, String value, String color) {
        MessageDataBean mdb = new MessageDataBean();
        mdb.setValue(value);
        mdb.setColor(color);
        this.addMessageData(key, mdb);
    }

}
