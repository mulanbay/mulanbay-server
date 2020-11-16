package cn.mulanbay.pms.handler.msg;

import cn.mulanbay.pms.persistent.domain.UserMessage;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class DelayMessage implements Delayed {

    // 消息
    private UserMessage message;

    // 延迟时长(毫秒)，这个是必须的属性因为要按照这个判断延时时长。
    private Date expectSendTime;

    public UserMessage getMessage() {
        return message;
    }

    public void setMessage(UserMessage message) {
        this.message = message;
    }

    public DelayMessage(UserMessage message) {
        if (message.getCreatedTime() == null) {
            message.setCreatedTime(new Date());
        }
        if (message.getExpectSendTime() == null) {
            message.setExpectSendTime(new Date());
        }
        this.message = message;
        this.expectSendTime = message.getExpectSendTime();
    }

    /**
     * 增加延迟
     *
     * @param milliseconds
     */
    public void addDelay(long milliseconds) {
        this.expectSendTime = new Date(this.expectSendTime.getTime() + milliseconds);
    }

    /**
     * 延迟任务是否到时就是按照这个方法判断如果返回的是负数则说明到期否则还没到期
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expectSendTime.getTime() - System.currentTimeMillis(),
                TimeUnit.MILLISECONDS);
    }

    /**
     * 优先队列里面优先级规则  TimeUnit .MILLISECONDS 获取单位 为毫秒的时间戳
     * 通过比较创建时间
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
