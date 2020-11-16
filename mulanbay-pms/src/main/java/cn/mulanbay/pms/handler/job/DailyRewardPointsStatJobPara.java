package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

/**
 * 积分统计JOB的参数定义
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class DailyRewardPointsStatJobPara extends AbstractTriggerPara {

    @JobParameter(name = "提醒时间", dataType = String.class, desc = "格式为08:30", editType = EditType.TIME)
    private String remindTime = "08:30";

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
}
