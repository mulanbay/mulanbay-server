package cn.mulanbay.pms.web.bean.response.auth;

public class RouterMetaVo {

    private String title;

    private String icon;

    /**
     * keep-alive缓存参数
     */
    private boolean noCache;

    public RouterMetaVo() {
    }

    public RouterMetaVo(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isNoCache() {
        return noCache;
    }

    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }
}
