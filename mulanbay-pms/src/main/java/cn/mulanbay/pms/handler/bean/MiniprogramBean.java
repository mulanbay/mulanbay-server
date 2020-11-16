package cn.mulanbay.pms.handler.bean;

/**
 * 微信模板消息封装
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class MiniprogramBean {

    /**
     * appid : xiaochengxuappid12345
     * pagepath : index?foo=bar
     */

    private String appid;
    private String pagepath;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

}
