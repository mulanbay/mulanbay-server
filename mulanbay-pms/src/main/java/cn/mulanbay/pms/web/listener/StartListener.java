package cn.mulanbay.pms.web.listener;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.HandlerManager;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 系统启动监听器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class StartListener extends BaseListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StartListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext ac = contextRefreshedEvent.getApplicationContext();
        BeanFactoryUtil.setApplicationContext(ac);
        HandlerManager hm = BeanFactoryUtil.getBean(HandlerManager.class);
        for (BaseHandler bh : hm.getHandlerList()) {
            logger.info(bh.getHandlerName() + " Handler begin to init...");
            bh.init();
            if (!bh.selfCheck()) {
                logger.error(bh.getHandlerName() + "自检失败。");
                if (bh.isScfShutdown()) {
                    logger.error("因" + bh.getHandlerName() + "自检失败，关闭系统。");
                    System.exit(-1);
                }
            }
            if (bh.isDoSystemLog()) {
                doLog(null, bh.getHandlerName() + "初始化", bh.getHandlerName() + "初始化成功");
            }
            logger.info(bh.getHandlerName() + " Handler init end");
        }
        logger.info("初始化了" + hm.getHandlerList().size() + "个Handler");
        doLog(ErrorCode.SYSTEM_STARTED, "系统启动", "系统启动成功");
    }

}
