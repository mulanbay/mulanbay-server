package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.HandlerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class ThreadPoolHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolHandler.class);

    @Value("${system.threadPool.corePoolSize}")
    int corePoolSize;

    @Value("${system.threadPool.queueSize}")
    int queueSize;

    @Value("${system.threadPool.maximumPoolSize}")
    int maximumPoolSize;

    private static ThreadPoolExecutor threadPool;

    public ThreadPoolHandler() {
        super("线程池");
    }

    /**
     * 初始化类时配置，而不是在init方法中配置
     * 原因是：其他的类在init可能就需要线程池的资源了
     */
    @PostConstruct
    public void initThread() {
        ThreadFactory threadFactory = new CustomizableThreadFactory("pmsThread");
        //日志采用丢弃策略：丢弃任务，但是不抛出异常。
        /**
         * ThreadPoolExecutor.AbortPolicy: 丢弃任务并抛出RejectedExecutionException异常。 (默认)
         ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
         */
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 10L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(queueSize),
                threadFactory, new ThreadPoolExecutor.DiscardPolicy());
    }

    @Override
    public void init() {
        logger.debug("初始化的线程池corePoolSize=" + corePoolSize + ",queueSize=" + queueSize);
    }

    /**
     * 添加线程
     *
     * @param thread
     */
    public void pushThread(Thread thread) {
        threadPool.execute(thread);
        logger.debug("往线程池中添加了一个线程:" + thread.getName());
    }

    public void pushThread(Runnable runnable) {
        threadPool.execute(runnable);
        logger.debug("往线程池中添加了一个线程:" + runnable.toString());
    }

    @Override
    public void destroy() {
        threadPool.shutdown();
    }

    @Override
    public HandlerInfo getHandlerInfo() {
        HandlerInfo hi = super.getHandlerInfo();
        hi.addDetail("ActiveCount", String.valueOf(threadPool.getActiveCount()));
        hi.addDetail("CompletedTaskCount", String.valueOf(threadPool.getCompletedTaskCount()));
        return hi;
    }

}
