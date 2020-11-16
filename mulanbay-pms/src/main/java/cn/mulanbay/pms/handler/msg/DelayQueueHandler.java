package cn.mulanbay.pms.handler.msg;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.lock.RedisDistributedLock;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.persistent.domain.UserMessage;
import cn.mulanbay.pms.persistent.service.UserRemindMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * 延迟队列处理器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class DelayQueueHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(DelayQueueHandler.class);

    private static DelayQueue<DelayMessage> queue = new DelayQueue<DelayMessage>();

    private static DelayMessageConsumeThread consumeThread;

    @Autowired
    RedisDistributedLock redisDistributedLock;

    @Autowired
    UserRemindMessageService userRemindMessageService;

    @Autowired
    BaseService baseService;

    public DelayQueueHandler() {
        super("延迟消息队列");
    }

    @Override
    public void init() {
        consumeThread = new DelayMessageConsumeThread(queue);
        consumeThread.start();
        logger.info("开启延迟消费队列线程");
        loadUnSendMessage();
    }

    private void loadUnSendMessage() {
        String key = "loadUnSendMessage";
        try {
            boolean b = redisDistributedLock.lock(key, 0);
            if (!b) {
                logger.warn("未发送消息队列已经被其他进程加载");
                return;
            }
            logger.info("开始加载未发送消息队列");
            List<UserMessage> list = userRemindMessageService.getNeedSendMessage(1, 1000, 3, null);
            if (list.isEmpty()) {
                logger.debug("没有需要加载的未发送消息队列");
            } else {
                for (UserMessage um : list) {
                    this.addMessage(um);
                }
                logger.info("一共加载了" + list.size() + "个未发送消息");
            }
            logger.info("加载未发送消息队列结束");
        } catch (Exception e) {
            logger.error("加载未发送消息队列异常", e);
        } finally {
            boolean b = redisDistributedLock.releaseLock(key);
            if (!b) {
                logger.warn("释放加载未发送消息队列锁key=" + key + "失败");
            }
        }
    }

    /**
     * 添加
     *
     * @param um
     */
    public void addMessage(UserMessage um) {
        queue.offer(new DelayMessage(um));
    }

    @Override
    public void destroy() {
        super.destroy();
        Iterator<DelayMessage> it = queue.iterator();
        int n = 0;
        while (it.hasNext()) {
            DelayMessage dm = it.next();
            UserMessage um = dm.getMessage();
            if (um.getId() == null) {
                //持久化
                baseService.saveObject(um);
                n++;
            }
        }
        logger.info("一共持久化了" + n + "个消息");
    }


}
