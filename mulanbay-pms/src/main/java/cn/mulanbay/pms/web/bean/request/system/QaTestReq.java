package cn.mulanbay.pms.web.bean.request.system;

import cn.mulanbay.common.aop.BindUser;
import cn.mulanbay.pms.persistent.enums.QaMessageSource;

public class QaTestReq implements BindUser {

    private Long userId;

    private String content;

    private QaMessageSource source;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QaMessageSource getSource() {
        return source;
    }

    public void setSource(QaMessageSource source) {
        this.source = source;
    }
}
