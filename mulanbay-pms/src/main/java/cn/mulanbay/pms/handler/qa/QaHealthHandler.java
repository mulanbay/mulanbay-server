package cn.mulanbay.pms.handler.qa;

import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import org.springframework.stereotype.Component;

/**
 * QA的健康处理器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class QaHealthHandler extends AbstractQaMessageHandler {


    public QaHealthHandler() {
        super("health", "QA的健康处理器");
    }

    @Override
    public String handleMsg(QaMatch match) {
        return "默认";
    }

    @Override
    public String parseExtraCondition(String keywords) {
        return super.parseExtraCondition(keywords);
    }
}
