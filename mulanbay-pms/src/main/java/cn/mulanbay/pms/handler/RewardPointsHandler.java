package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.pms.persistent.enums.RewardSource;
import cn.mulanbay.pms.thread.RewardPointsThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 积分奖励
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class RewardPointsHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(RewardPointsHandler.class);

    @Autowired
    ThreadPoolHandler threadPoolHandler;

    public RewardPointsHandler() {
        super("积分等级处理");
    }

    public void rewardPoints(Long userId, int rewards, Long sourceId, RewardSource rewardSource, String remark, Long messageId) {
        if (userId == null) {
            logger.warn("当前用户信息为空，无法进行积分奖励");
        }
        RewardPointsThread thread = new RewardPointsThread(userId, rewards, sourceId, rewardSource, remark, messageId);
        threadPoolHandler.pushThread(thread);
    }


}
