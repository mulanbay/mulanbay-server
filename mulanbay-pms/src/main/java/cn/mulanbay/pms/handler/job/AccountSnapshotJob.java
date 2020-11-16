package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.persistent.service.AccountService;
import cn.mulanbay.schedule.ParaCheckResult;
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

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        AccountService accountService = BeanFactoryUtil.getBean(AccountService.class);
        //快照是当前的时间,key为当前日期
        Date bussDay = new Date();
        String bussKey = DateUtil.getFormatDate(bussDay, DateUtil.Format24Datetime2);
        //accountService.createSnapshot(bussKey,null);
        tr.setExecuteResult(JobExecuteResult.SUCCESS);
        return tr;
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return null;
    }
}
