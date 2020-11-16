package mulanbay;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.handler.msg.DelayMessage;
import cn.mulanbay.pms.persistent.domain.UserMessage;

import java.util.concurrent.DelayQueue;

/**
 * @author fenghong
 * @title: DelayConsume
 * @description: TODO
 * @date 2019-08-02 15:26
 */
public class DelayConsume extends Thread {

    private DelayQueue<DelayMessage> queue;

    public DelayConsume(DelayQueue<DelayMessage> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DelayMessage dm = queue.take();
                UserMessage message = dm.getMessage();
                System.out.println("message:" + message.getTitle() + "-->" + DateUtil.getFormatDate(message.getExpectSendTime(), DateUtil.Format24Datetime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
