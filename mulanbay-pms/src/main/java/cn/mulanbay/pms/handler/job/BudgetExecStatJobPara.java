package cn.mulanbay.pms.handler.job;

import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

/**
 * 预算执行统计的参数定义
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BudgetExecStatJobPara extends AbstractTriggerPara {

    @JobParameter(name = "周期类型", dataType = String.class, editType = EditType.COMBOBOX,
            editData = "[{\"id\":\"MONTHLY\",\"text\":\"月度预算\"},{\"id\":\"YEARLY\",\"text\":\"年度预算\"}]\n",
            desc = "周期类型")
    private PeriodType period;

    @JobParameter(name = "超出预算积分得分", dataType = Integer.class, desc = "小于0的数字", editType = EditType.NUMBER)
    private int overBudgetScore = -50;

    @JobParameter(name = "在预算内积分得分", dataType = String.class, desc = "大于0的数字", editType = EditType.NUMBER)
    private int lessBudgetScore = 50;

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
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
}
