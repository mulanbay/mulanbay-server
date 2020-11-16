package cn.mulanbay.pms.handler.msg;

import cn.mulanbay.common.thread.EnhanceThread;
import cn.mulanbay.common.thread.ThreadInfo;
import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.handler.PmsMessageSendHandler;
import cn.mulanbay.pms.persistent.domain.UserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 延迟队列消费线程
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class DelayMessageConsumeThread extends EnhanceThread {

    private static final Logger logger = LoggerFactory.getLogger(DelayMessageConsumeThread.class);

    private DelayQueue<DelayMessage> queue;

    private AtomicLong total = new AtomicLong(0);

    private AtomicLong fails = new AtomicLong(0);

    public DelayMessageConsumeThread(DelayQueue<DelayMessage> queue) {
        super("延迟队列消费线程");
        this.queue = queue;
    }

    @Override
    public void doTask() {
        PmsMessageSendHandler sendHandler = BeanFactoryUtil.getBean(PmsMessageSendHandler.class);
        while (!isStop) {
            try {
                DelayMessage dm = queue.take();
                UserMessage message = dm.getMessage();
                total.getAndIncrement();
                boolean res = sendHandler.sendMessage(message);
                if (!res && message.getFailCount() < 3) {
                    //发送失败延迟1秒重新发送
                    dm.addDelay(1000);
                    queue.offer(dm);
                    fails.getAndIncrement();
                }
                logger.debug("从延迟队列消费了一个消息");
            } catch (Exception e) {
                logger.error("从延迟队列消费消息异常", e);
            }
        }
    }

    @Override
    public List<ThreadInfo> getThreadInfo() {
        List<ThreadInfo> list = new ArrayList<>();
        Iterator<DelayMessage> it = queue.iterator();
        int n = 0;
        while (it.hasNext()) {
            DelayMessage dm = it.next();
            if (n < 10) {
                //最多添加十个
                String v = dm.getMessage().getTitle() + ":" + DateUtil.getFormatDate(dm.getMessage().getExpectSendTime(), DateUtil.Format24Datetime);
                list.add(new ThreadInfo("第" + (n + 1) + "个消息", v));
            }
            n++;
        }
        list.add(new ThreadInfo("队列中消息数", n + "个"));
        list.add(new ThreadInfo("总消费消息数", total.get() + "个"));
        list.add(new ThreadInfo("总发送失败消息数", fails.get() + "个"));
        return list;
    }
}
