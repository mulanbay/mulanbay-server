package mulanbay;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.handler.msg.DelayMessage;
import cn.mulanbay.pms.persistent.domain.UserMessage;

import java.util.concurrent.DelayQueue;

/**
 * @author fenghong
 * @title: DelayTest
 * @description: TODO
 * @date 2019-08-02 15:28
 */
public class DelayTest {

    public static void main(String[] args) {
        DelayQueue<DelayMessage> queue = new DelayQueue<>();
        UserMessage m1 = new UserMessage();
        m1.setExpectSendTime(DateUtil.getDate("2019-08-01 15:30:00", DateUtil.Format24Datetime));
        m1.setTitle("t1");
        queue.offer(new DelayMessage(m1));

        UserMessage m2 = new UserMessage();
        m2.setExpectSendTime(DateUtil.getDate("2019-08-03 15:33:00", DateUtil.Format24Datetime));
        m2.setTitle("t2");
        queue.offer(new DelayMessage(m2));

        UserMessage m3 = new UserMessage();
        m3.setExpectSendTime(DateUtil.getDate("2019-08-08 15:34:00", DateUtil.Format24Datetime));
        m3.setTitle("t3");
        queue.offer(new DelayMessage(m3));

        DelayConsume dc = new DelayConsume(queue);
        dc.start();
    }

}
