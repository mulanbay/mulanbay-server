package cn.mulanbay.pms.web.bean.response.auth;

import java.util.ArrayList;
import java.util.List;

public class RouterVo {

    private Long id;

    private String name;

    private String path;

    private String redirect;

    private Boolean alwaysShow;

    private String component;

    private Boolean hidden;

    private RouterMetaVo meta;

    private List<RouterVo> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Boolean isAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public RouterMetaVo getMeta() {
        return meta;
    }

    public void setMeta(RouterMetaVo meta) {
        this.meta = meta;
    }

    public List<RouterVo> getChildren() {
        return children;
    }

    public void setChildren(List<RouterVo> children) {
        this.children = children;
    }

    /**
     * 添加
     *
     * @param rb
     */
    public void addChild(RouterVo rb) {
        this.children.add(rb);
    }
}
