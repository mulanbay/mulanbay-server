package cn.mulanbay.pms.handler.bean;

import java.io.Serializable;

/**
 * 股票评分配置
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class SharesScoreConfigDetail implements Serializable {

    private static final long serialVersionUID = -4368468253208280614L;

    private double min;

    private double max;

    private int score;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
