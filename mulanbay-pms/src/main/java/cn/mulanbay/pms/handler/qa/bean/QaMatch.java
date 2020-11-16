package cn.mulanbay.pms.handler.qa.bean;


import cn.mulanbay.pms.persistent.enums.QaMessageSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * QA匹配封装
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class QaMatch implements Serializable {

    private static final long serialVersionUID = -3369825505496057L;

    private Long userId;

    private String sessionId;

    //当有多个返回值时判断使用
    private QaMessageSource source;

    private String keywords;

    private List<QaMatchDetail> matchDetails;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public QaMessageSource getSource() {
        return source;
    }

    public void setSource(QaMessageSource source) {
        this.source = source;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<QaMatchDetail> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(List<QaMatchDetail> matchDetails) {
        this.matchDetails = matchDetails;
    }

    /**
     * 新增
     *
     * @param qaConfig
     * @param matchDegree
     */
    public void addMatch(QaConfigBean qaConfig, float matchDegree) {
        QaMatchDetail md = new QaMatchDetail();
        md.setMatchDegree(matchDegree);
        md.setQaConfig(qaConfig);
        addMatch(md);
    }

    public void addMatch(QaMatchDetail md) {
        if (matchDetails == null) {
            matchDetails = new ArrayList<>();
        }
        md.setLevel(matchDetails.size() + 1);
        matchDetails.add(md);
    }

    public QaConfigBean getFirstMathQa() {
        if (matchDetails == null || matchDetails.isEmpty()) {
            return null;
        } else {
            return matchDetails.get(0).getQaConfig();
        }
    }

    public QaConfigBean getLastMathQa() {
        if (matchDetails == null || matchDetails.isEmpty()) {
            return null;
        } else {
            int index = matchDetails.size() - 1;
            return matchDetails.get(index).getQaConfig();
        }
    }
}
