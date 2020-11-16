package cn.mulanbay.pms.handler.qa.bean;

import java.io.Serializable;

/**
 * 针对连续的请求使用
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class QaMatchCache implements Serializable {

    private static final long serialVersionUID = -552145908971412214L;

    private int page = 1;

    private QaMatch match;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public QaMatch getMatch() {
        return match;
    }

    public void setMatch(QaMatch match) {
        this.match = match;
    }

}
