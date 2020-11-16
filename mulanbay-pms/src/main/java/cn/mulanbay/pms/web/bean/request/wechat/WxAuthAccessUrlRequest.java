package cn.mulanbay.pms.web.bean.request.wechat;

public class WxAuthAccessUrlRequest {

    private String scope;

    private String redirectUri;

    //用户个性值
    private String state;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
