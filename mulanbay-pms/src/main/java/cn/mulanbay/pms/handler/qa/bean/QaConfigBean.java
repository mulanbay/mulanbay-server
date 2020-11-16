package cn.mulanbay.pms.handler.qa.bean;

import cn.mulanbay.pms.persistent.domain.QaConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * QA配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class QaConfigBean extends QaConfig implements Serializable {

    private static final long serialVersionUID = 9222500333212694319L;

    private List<QaConfigBean> children;

    public List<QaConfigBean> getChildren() {
        return children;
    }

    public void setChildren(List<QaConfigBean> children) {
        this.children = children;
    }

    /**
     * 添加子类
     *
     * @param qc
     */
    public void addChild(QaConfigBean qc) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(qc);
    }

    public boolean hasChildren() {
        if (children == null || children.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
