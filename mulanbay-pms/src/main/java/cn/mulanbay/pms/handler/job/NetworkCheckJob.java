package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.http.HttpResult;
import cn.mulanbay.common.http.HttpUtil;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.apache.http.HttpStatus;

import java.util.Date;

/**
 * @author fenghong
 * @title: NetworkCheckJob
 * @description: 检测网络是否正常且发送恢复通知
 * @create 2017-07-10 21:44
 */
public class NetworkCheckJob extends AbstractBaseJob {

    private NetworkCheckJobPara para;

    private static boolean lastStatus = true;

    private static Date lastStatusTime = new Date();

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        HttpResult hr = HttpUtil.doHttpGet(para.getUrl());
        boolean b;
        if (hr.getStatusCode() == HttpStatus.SC_OK) {
            b = true;
        } else {
            b = false;
        }
        Date now = new Date();
        if (b == true && lastStatus == false) {
            //网络由异常转换为正常
            String title = "网络恢复正常";
            String content = "网络恢复正常，持续时间:" + DateUtil.getFormatDate(lastStatusTime, DateUtil.Format24Datetime)
                    + "到" + DateUtil.getFormatDate(now, DateUtil.Format24Datetime);
            PmsNotifyHandler notifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
            notifyHandler.addMessageToNotifier(PmsErrorCode.NETWORK_RE_OK, title, content, now, null);
        }
        if (b != lastStatus) {
            //设置新的状态
            lastStatus = b;
            lastStatusTime = now;
        }
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
        return NetworkCheckJobPara.class;
    }
}
