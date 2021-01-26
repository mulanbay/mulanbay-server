package cn.mulanbay.pms.web.bean.response;

import cn.mulanbay.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TreeBean {

    private String id;

    private String pid;

    private String text;

    private boolean checked;

    private String iconCls;

    private String group;

    private Attribute attributes;

    List<TreeBean> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<TreeBean> children) {
        this.children = children;
    }

    public void addChild(TreeBean child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public Attribute getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * 支持vue-Treeselect
     *
     * @return
     */
    public String getLabel() {
        return text;
    }

    /**
     * 支持 vant
     *
     * @return
     */
    public String getName() {
        return text;
    }

    public boolean hasChildren() {
        return StringUtil.isEmpty(this.children) ? false : true;
    }
}
