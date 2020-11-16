package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.pms.persistent.domain.DatabaseClean;
import cn.mulanbay.pms.persistent.service.DatabaseCleanService;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;

import java.util.List;

/**
 * 数据库清理任务
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class DatabaseCleanJob extends AbstractBaseJob {

    @Override
    public TaskResult doTask() {
        DatabaseCleanService databaseCleanService = BeanFactoryUtil.getBean(DatabaseCleanService.class);
        List<DatabaseClean> list = databaseCleanService.getActiveDatabaseClean();
        for (DatabaseClean dc : list) {
            databaseCleanService.updateAndExecDatabaseClean(dc);
        }
        return new TaskResult(JobExecuteResult.SUCCESS);
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
