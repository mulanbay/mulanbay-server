package cn.mulanbay.pms.handler.qa.bean;

/**
 *
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class QaMatchLog{

    private Long qaConfigId;

    private String qaConfigName;

    //匹配级数
    private int level;

    //匹配度
    private float matchDegree;

    public Long getQaConfigId() {
        return qaConfigId;
    }

    public void setQaConfigId(Long qaConfigId) {
        this.qaConfigId = qaConfigId;
    }

    public String getQaConfigName() {
        return qaConfigName;
    }

    public void setQaConfigName(String qaConfigName) {
        this.qaConfigName = qaConfigName;
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
