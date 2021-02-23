package cn.mulanbay.pms.handler.msg;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.HandlerCmd;
import cn.mulanbay.business.handler.HandlerInfo;
import cn.mulanbay.business.handler.HandlerResult;
import cn.mulanbay.business.handler.lock.RedisDistributedLock;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.persistent.domain.UserMessage;
import cn.mulanbay.pms.persistent.service.UserRemindMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 基于redis的SortsSet的延迟队列
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class RedisDelayQueueHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisDelayQueueHandler.class);

    @Value("${system.namespace}")
    String namespace;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RedisDistributedLock redisDistributedLock;

    @Autowired
    UserRemindMessageService userRemindMessageService;

    @Autowired
    BaseService baseService;

    private String messageQueue = "userMessage";

    public RedisDelayQueueHandler() {
        super("Redis延迟消息队列");
    }

    @Override
    public void init() {
    }

    /**
     * redis缓存被清空的时候才需要
     */
    private void loadUnSendMessage() {
        String key = "loadUnSendMessage";
        try {
            boolean b = redisDistributedLock.lock(key, 0);
            if (!b) {
                logger.warn("未发送消息队列已经被其他进程加载");
                return;
            }
            //第一步：清除
            redisTemplate.delete(this.getQueueName());
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
        try {
            ZSetOperations zSetOperations = redisTemplate.opsForZSet();
            String key = getQueueName();
            if (um.getExpectSendTime() == null) {
                um.setExpectSendTime(new Date());
            }
            //以预期发送时间顺序排列
            double score = um.getExpectSendTime().getTime();
            zSetOperations.add(key, um, score);
        } catch (Exception e) {
            logger.error("向Redis中添加消息异常", e);
        }
    }

    /**
     * 删除
     *
     * @param um
     */
    public void removeMessage(UserMessage um) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        String key = getQueueName();
        zSetOperations.remove(key, um);
    }

    /**
     * 清除所有的未发送消息
     */
    public void clearMessage() {
        redisTemplate.delete(this.getQueueName());
    }

    /**
     * 获取队列名
     *
     * @return
     */
    private String getQueueName() {
        return namespace + ":" + messageQueue;
    }

    /**
     * 获取需要发送的消息
     *
     * @param now
     * @return
     */
    public Set<UserMessage> getNeedSendMessage(Date now) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set<UserMessage> sets = zSetOperations.rangeByScore(getQueueName(), 0, now.getTime());
        return sets;
    }

    @Override
    public List<HandlerCmd> getSupportCmdList() {
        List<HandlerCmd> list = new ArrayList<>();
        list.add(new HandlerCmd("loadUnSendMessage", "加载数据库中未发送消息"));
        return list;
    }

    @Override
    public HandlerResult handle(String cmd) {
        if ("loadUnSendMessage".equals(cmd)) {
            loadUnSendMessage();
        }
        return super.handle(cmd);
    }

    @Override
    public HandlerInfo getHandlerInfo() {
        HandlerInfo hi = super.getHandlerInfo();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        String key = getQueueName();
        double max = System.currentTimeMillis();
        //10天内需要发送的消息（不包含已经过期的）
        double max10 = max + 10 * 24 * 3600 * 1000;
        hi.addDetail("已经过期的总消息数", zSetOperations.count(key, 0, max).toString());
        hi.addDetail("10天内需要发送的总消息数", zSetOperations.count(key, max, max10).toString());
        return hi;
    }

}
