package cn.mulanbay.pms.handler.qa;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.handler.qa.bean.QaConfigBean;
import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import cn.mulanbay.pms.handler.qa.bean.QaMatchDetail;
import cn.mulanbay.pms.handler.qa.bean.QaResult;
import cn.mulanbay.pms.persistent.domain.QaConfig;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.QaMessageSource;
import cn.mulanbay.pms.persistent.enums.QaResultType;
import cn.mulanbay.pms.web.bean.request.system.QaConfigSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * QA处理入口
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class QaHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(QaHandler.class);

    @Value("${system.word2Vector.minMatchDegree}")
    float minMatchDegree;

    @Value("${system.word2Vector.maxMatchDegree}")
    float maxMatchDegree;

    @Autowired
    BaseService baseService;

    @Autowired
    AhaNLPHandler ahaNLPHandler;

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    List<AbstractQaMessageHandler> messageHandlerList;

    public QaHandler() {
        super("QA处理");
    }

    @Override
    public void init() {
        super.init();
        loadQaList();
        logger.info("加载了" + messageHandlerList.size() + "个QA处理器");
    }

    private void loadQaList() {
        QaConfigSearch sf = new QaConfigSearch();
        sf.setStatus(CommonStatus.ENABLE);
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(QaConfig.class);
        pr.setPage(0);
        pr.addSort(new Sort("orderIndex", Sort.ASC));
        List<QaConfig> qaList = baseService.getBeanList(pr);
        List<QaConfigBean> list = new ArrayList<>();
        for (QaConfig qc : qaList) {
            //设置第一级
            if (qc.getParentId() == null) {
                QaConfigBean bean = new QaConfigBean();
                BeanCopy.copyProperties(qc, bean);
                setChildren(bean, qaList);
                list.add(bean);
            }
        }
        cacheHandler.set(CacheKey.QA_LIST, list, 0);
        logger.info("初始化了" + qaList.size() + "个QA处理配置");
    }

    /**
     * 设置子类
     *
     * @param bean
     * @param qaList
     */
    private void setChildren(QaConfigBean bean, List<QaConfig> qaList) {
        for (QaConfig qc : qaList) {
            if (qc.getParentId()!=null&&qc.getParentId().equals(bean.getId())) {
                QaConfigBean child = new QaConfigBean();
                BeanCopy.copyProperties(qc, child);
                bean.addChild(child);
                setChildren(child, qaList);
            }
        }
    }

    /**
     * 处理主入口
     *
     * @param source
     * @param content
     * @param userId
     * @param sessionId 上下文级联使用：比如翻页
     * @return
     */
    public QaResult handleMessage(QaMessageSource source, String content, Long userId, String sessionId) {
        QaResult cr = new QaResult();
        try {
            QaMatch match = handleNLPMatch(content);
            match.setSource(source);
            match.setUserId(userId);
            match.setSessionId(sessionId);
            logMatch(match);
            //获取第一个匹配
            QaConfig qa = match.getFirstMathQa();
            if (qa == null) {
                //切换到默认的Qa
                qa = getQaConfig(0L);
            }
            //logger.debug("NLPMatch:"+qa.getId()+","+qa.getName());
            //跳转到指定QA
            if (qa.getReferQaId() != null) {
                //切换到具体的处理类
                qa = this.getQaConfig(qa.getReferQaId());
            }
            cr.setQa(qa);
            QaResultType resultType = qa.getResultType();
            if (resultType == QaResultType.DIRECT) {
                cr.setRes(qa.getReplayContent());
            } else {
                //找到具体的处理类（以第一级匹配到的QA）
                AbstractQaMessageHandler messageHandler = this.getMessageHandler(qa.getHandleCode());
                if (messageHandler == null) {
                    //采用默认处理器
                    messageHandler = this.getMessageHandler("default");
                }
                String reply = messageHandler.handleMatch(match);
                cr.setRes(reply);
                return cr;
            }
        } catch (Exception e) {
            logger.error("处理请求异常", e);
            cr.setRes("处理请求异常");
        }
        return cr;
    }

    /**
     * 打印匹配的日志
     *
     * @param match
     */
    private void logMatch(QaMatch match) {
        if (logger.isDebugEnabled()) {
            List<QaMatchDetail> matchDetails = match.getMatchDetails();
            if (StringUtil.isNotEmpty(matchDetails)) {
                for (QaMatchDetail md : matchDetails) {
                    QaConfigBean qcb = md.getQaConfig();
                    if (qcb == null) {
                        logger.debug("第" + md.getLevel() + "级未找到匹配");
                    } else {
                        logger.debug("第" + md.getLevel() + "级匹配:" + md.getQaConfig().getName() + ",匹配度:" + md.getMatchDegree());
                    }
                }
            } else {
                logger.warn("没有任何匹配");
            }
        }
    }

    /**
     * 处理NLP匹配
     *
     * @param content
     * @return
     */
    private QaMatch handleNLPMatch(String content) {
        //第一步，通过语义距离来判断是哪一个
        QaMatch match = new QaMatch();
        match.setKeywords(content);
        List<QaConfigBean> qas = this.getQaList();
        QaMatchDetail md = this.doNLPMatch(content, qas);
        match.addMatch(md);
        if (md.getQaConfig() != null) {
            //如果匹配出来还有子类，再匹配
            setDeepMatch(match, md.getQaConfig(), content);
        }
        return match;
    }

    /**
     * 设置多层匹配
     *
     * @param match
     * @param qcb
     * @param content
     */
    private void setDeepMatch(QaMatch match, QaConfigBean qcb, String content) {
        if (qcb != null && qcb.hasChildren()) {
            QaMatchDetail deep = doNLPMatch(content, qcb.getChildren());
            match.addMatch(deep);
            setDeepMatch(match, deep.getQaConfig(), content);
        }
    }

    /**
     * 处理单层的匹配
     *
     * @param content
     * @param qas
     * @return
     */
    private QaMatchDetail doNLPMatch(String content, List<QaConfigBean> qas) {
        //第一步，通过语义距离来判断是哪一个
        QaMatchDetail match = new QaMatchDetail();
        QaConfigBean matchQa = null;
        //匹配度
        float v = 0;
        for (QaConfigBean qa : qas) {
            float n = ahaNLPHandler.sentenceSimilarity(content, qa.getKeywords());
            //logger.debug(content+" 和 "+qa.getKeywords()+" 的匹配度:"+n+",QAId:"+qa.getId());
            if (n > v) {
                v = n;
                matchQa = qa;
            }
            if (v >= maxMatchDegree) {
                logger.debug("匹配到高匹配的QA");
                break;
            }
        }
        if (v < minMatchDegree) {
            //认为没有匹配到
            matchQa = null;
        }
        match.setQaConfig(matchQa);
        match.setMatchDegree(v);
        return match;
    }

    /**
     * 获取处理器
     *
     * @param handleCode
     * @return
     */
    private AbstractQaMessageHandler getMessageHandler(String handleCode) {
        for (AbstractQaMessageHandler handler : messageHandlerList) {
            if (handleCode.equals(handler.getCode())) {
                return handler;
            }
        }
        return null;
    }

    private QaConfigBean getQaConfig(Long id) {
        List<QaConfigBean> qas = getQaList();
        for (QaConfigBean qa : qas) {
            if (qa.getId().equals(id)) {
                return qa;
            }
        }
        return null;
    }

    private List<QaConfigBean> getQaList() {
        return (List<QaConfigBean>) cacheHandler.get(CacheKey.QA_LIST);
    }

    @Override
    public void reload() {
        loadQaList();
    }
}
