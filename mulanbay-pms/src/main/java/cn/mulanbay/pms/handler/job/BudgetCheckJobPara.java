package cn.mulanbay.pms.handler.job;

import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

/**
 * 预算检查JOB的参数定义
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BudgetCheckJobPara extends AbstractTriggerPara {

    @JobParameter(name = "周期类型", dataType = String.class, editType = EditType.COMBOBOX,
            editData = "[{\"id\":\"MONTHLY\",\"text\":\"月度预算\"},{\"id\":\"YEARLY\",\"text\":\"年度预算\"}]\n",
            desc = "周期类型")
    private PeriodType period;

    @JobParameter(name = "可控的浮动比例", dataType = Integer.class, desc = "%", editType = EditType.NUMBER)
    private int floatRate = 5;

    @JobParameter(name = "超出预算积分得分", dataType = Integer.class, desc = "小于0的数字", editType = EditType.NUMBER)
    private int overBudgetScore = -1;

    @JobParameter(name = "在预算内积分得分", dataType = Integer.class, desc = "大于0的数字", editType = EditType.NUMBER)
    private int lessBudgetScore = 1;

    @JobParameter(name = "检查的时间比例", dataType = Integer.class, desc = "如50表示，该月或该年过去50%才开始执行", editType = EditType.NUMBER)
    private int checkFromRate = 70;

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public int getFloatRate() {
        return floatRate;
    }

    public void setFloatRate(int floatRate) {
        this.floatRate = floatRate;
    }

    public int getOverBudgetScore() {
        return overBudgetScore;
    }

    public void setOverBudgetScore(int overBudgetScore) {
        this.overBudgetScore = overBudgetScore;
    }

    public int getLessBudgetScore() {
        return lessBudgetScore;
    }

    public void setLessBudgetScore(int lessBudgetScore) {
        this.lessBudgetScore = lessBudgetScore;
    }

    public int getCheckFromRate() {
        return checkFromRate;
    }

    public void setCheckFromRate(int checkFromRate) {
        this.checkFromRate = checkFromRate;
    }
}
