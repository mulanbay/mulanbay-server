package cn.mulanbay.pms.web.bean.request.notify;

import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;

public class NotifyConfigTreeSearch extends CommonTreeSearch {

    private String relatedBeans;

    public String getRelatedBeans() {
        return relatedBeans;
    }

    public void setRelatedBeans(String relatedBeans) {
        this.relatedBeans = relatedBeans;
    }

}
