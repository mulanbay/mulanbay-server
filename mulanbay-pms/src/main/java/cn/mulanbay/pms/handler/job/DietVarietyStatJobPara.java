package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

/**
 * 饮食多样性统计的参数定义
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class DietVarietyStatJobPara extends AbstractTriggerPara {

    @JobParameter(name = "统计的天数", dataType = Integer.class, desc = "值：默认一个星期", editType = EditType.NUMBER)
    private int days = 7;

    @JobParameter(name = "排序方式", dataType = String.class, editType = EditType.COMBOBOX,
            editData = "[{\"id\":\"occur_time\",\"text\":\"时间\"},{\"id\":\"foods\",\"text\":\"食物\"}]\n",
            desc = "周期类型")
    private String orderByField;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }
}
