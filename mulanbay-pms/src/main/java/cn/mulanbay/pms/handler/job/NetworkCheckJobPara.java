package cn.mulanbay.pms.handler.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.JobParameter;

/**
 * @author fenghong
 * @title: NetworkCheckJob
 * @description: 检测网络是否正常且发送恢复通知
 * @create 2017-07-10 21:44
 */
public class NetworkCheckJobPara extends AbstractTriggerPara {

    @JobParameter(name = "检测地址", dataType = String.class)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
