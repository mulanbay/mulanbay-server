package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

/**
 * 预算提醒JOB参数定义
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BudgetRemindJobPara extends AbstractTriggerPara {

    @JobParameter(name = "预算未完成积分的得分", dataType = Integer.class, desc = "小于0的数字", editType = EditType.NUMBER)
    private int score = -50;

    @JobParameter(name = "预算时间到之前多少天", dataType = Integer.class, desc = "小于0的数字", editType = EditType.NUMBER)
    private int minDays = 5;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }
}
