package cn.mulanbay.schedule.job;

import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;

/**
 * ${DESCRIPTION}
 * 只供测试使用
 *
 * @author fenghong
 * @create 2017-10-19 10:13
 **/
public class TestJob extends AbstractBaseJob {

    boolean isRandom=false;

    @Override
    public TaskResult doTask() {
        if(isRandom){
            int n = Integer.valueOf(NumberUtil.getRandNum(1));
            if(n<=3){
                return new TaskResult(JobExecuteResult.SUCCESS);
            }else if(n<=6){
                return new TaskResult(JobExecuteResult.FAIL);
            }else {
                return new TaskResult(JobExecuteResult.SKIP);
            }
        }else{
            return new TaskResult();
        }

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
