package cn.mulanbay.pms.util;

import cn.mulanbay.pms.persistent.domain.Dream;
import cn.mulanbay.pms.persistent.enums.DreamStatus;

/**
 * 梦想操作类
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
public class DreamUtil {

    //  比例系数，合起来100
    private static int DIFFICULTY_RATE = 15;

    private static int IMPORTANT_LEVEL_RATE = 35;

    private static int STATUS_RATE = 0;

    private static int RATE_RATE = 20;

    private static int LEFT_DAYS = 30;

    /**
     * 计算紧急度得分值
     *
     * @param dream
     * @return
     */
    public static int getEmergencyScore(Dream dream) {
        if (dream.getStatus() == null || dream.getStatus() == DreamStatus.FINISHED || dream.getStatus() == DreamStatus.GIVEDUP || dream.getStatus() == DreamStatus.PAUSED) {
            return 0;
        } else {
            int result = 0;
            result += DIFFICULTY_RATE * (dream.getDifficulty().doubleValue() / 10);
            result += IMPORTANT_LEVEL_RATE * (dream.getImportantLevel().doubleValue() / 5);
            result += RATE_RATE * ((100.0 - dream.getRate().doubleValue()) / 100);
            int leftDays = dream.getLeftDays();
            if (leftDays <= 0) {
                return 0;
            } else {
                if (leftDays < 30) {
                    result += 30;
                } else if (leftDays < 90) {
                    result += 20;
                } else if (leftDays < 365) {
                    result += 10;
                } else if (leftDays < 365 * 3) {
                    result += 5;
                } else {
                    result += 2;
                }
            }
            return result;
        }
    }

}
