package cn.mulanbay.pms.handler.qa;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.handler.qa.bean.QaConfigBean;
import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import cn.mulanbay.pms.handler.qa.bean.QaMatchCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 翻页处理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class QaScrollPageHandler extends AbstractQaMessageHandler {

    @Autowired
    CacheHandler cacheHandler;

    public QaScrollPageHandler() {
        super("scroll_page", "QA翻页处理");
    }

    @Override
    public String handleMsg(QaMatch match) {
        //提取页码
        int offset = this.parsePageOffset(match.getKeywords());
        String key = CacheKey.getKey(CacheKey.QA_MATCH_CACHE, match.getSessionId());
        QaMatchCache matchCache = cacheHandler.get(key, QaMatchCache.class);
        if (matchCache == null) {
            return "无效的页面请求，请输入您想要查询/操作的功能";
        } else {
            int page = matchCache.getPage();
            page += offset;
            if (page < 1) {
                return "无法再往上翻页,页码：" + page;
            }
            matchCache.setPage(page);
            putCache(matchCache);
            QaMatch cache = matchCache.getMatch();
            QaConfigBean qcb = cache.getLastMathQa();
            return this.handleSqlReplay(qcb, cache.getKeywords(), cache.getUserId(), matchCache.getPage());
        }
    }

    private int parsePageOffset(String keywords) {
        if (keywords.contains("下一页")) {
            return 1;
        } else if (keywords.contains("上一页")) {
            return -1;
        }
        //默认往下一页
        return 1;
    }
}
