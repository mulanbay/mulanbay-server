package cn.mulanbay.pms.web.listener;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.HandlerManager;
import cn.mulanbay.common.thread.ThreadManager;
import cn.mulanbay.common.util.BeanFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * 系统关闭监听器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class StopListener extends BaseListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StopListener.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {

        logger.info("SpringContext begin to destroy....................");
        HandlerManager hm = BeanFactoryUtil.getBean(HandlerManager.class);
        for (BaseHandler bh : hm.getHandlerList()) {
            logger.info(bh.getHandlerName() + " Handler begin to destroy...");
            bh.destroy();
            if (bh.isDoSystemLog()) {
                doLog(null, bh.getHandlerName() + "关闭", bh.getHandlerName() + "关闭成功");
            }
            logger.error(bh.getHandlerName() + " Handler destroyed。");
        }
        logger.info("关闭了" + hm.getHandlerList().size() + "个Handler");
        ThreadManager.getInstance().stopAll();
        logger.info("SpringContext Destroyed....................");
    }

}
