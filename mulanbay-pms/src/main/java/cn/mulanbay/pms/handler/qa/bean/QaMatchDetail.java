package cn.mulanbay.pms.handler.qa.bean;

import java.io.Serializable;

/**
 *
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class QaMatchDetail implements Serializable {

    private static final long serialVersionUID = -2413626270564783427L;
    private QaConfigBean qaConfig;

    //匹配级数
    private int level;

    //匹配度
    private float matchDegree;

    public QaConfigBean getQaConfig() {
        return qaConfig;
    }

    public void setQaConfig(QaConfigBean qaConfig) {
        this.qaConfig = qaConfig;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getMatchDegree() {
        return matchDegree;
    }

    public void setMatchDegree(float matchDegree) {
        this.matchDegree = matchDegree;
    }
}
