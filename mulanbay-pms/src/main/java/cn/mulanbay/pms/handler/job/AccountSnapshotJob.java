package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.persistent.service.AccountService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;

import java.util.Date;

/**
 * 创建账户快照
 * 如果账户的余额流水能和第三方自动同步，该job有意义
 * 但是目前账户的修改维护都是手动完成的，所以该job目前没有作用
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class AccountSnapshotJob extends AbstractBaseJob {

    private AccountSnapshotJobPara para;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        AccountService accountService = BeanFactoryUtil.getBean(AccountService.class);
        //快照是当前的时间,key为运营日期
        Date bussDay = this.getBussDay();
        BudgetHandler budgetHandler = BeanFactoryUtil.getBean(BudgetHandler.class);
        String bussKey = budgetHandler.createBussKey(para.getPeriod(), bussDay);
        //获取用户列表
        //accountService.createSnapshot(bussKey,null);
        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        return tr;
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        ParaCheckResult result = new ParaCheckResult();
        para = this.getTriggerParaBean();
        if (para == null) {
            result.setErrorCode(ScheduleErrorCode.TRIGGER_PARA_NULL);
            result.setMessage("调度参数检查失败，参数为空");
        }
        return result;
    }

    @Override
    public Class getParaDefine() {
        return AccountSnapshotJobPara.class;
    }
}
