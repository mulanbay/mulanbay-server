package cn.mulanbay.pms.handler.bean;

/**
 * 微信token
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class WxAccessToken {

    private String access_token;

    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

}
