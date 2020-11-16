package cn.mulanbay.pms.handler.qa;

import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import org.springframework.stereotype.Component;

/**
 * 默认QA处理器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class QaDefaultHandler extends AbstractQaMessageHandler {

    public QaDefaultHandler() {
        super("default", "默认QA处理器");
    }

    @Override
    public String handleMsg(QaMatch match) {
        return "默认";
    }
}
