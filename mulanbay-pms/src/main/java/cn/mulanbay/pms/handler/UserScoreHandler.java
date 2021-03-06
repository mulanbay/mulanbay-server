package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.pms.persistent.domain.ScoreConfig;
import cn.mulanbay.pms.persistent.domain.UserScore;
import cn.mulanbay.pms.persistent.domain.UserScoreDetail;
import cn.mulanbay.pms.persistent.domain.UserSetting;
import cn.mulanbay.pms.persistent.enums.CompareType;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fenghong
 * @title: UserScoreHandler
 * @description: 用户评分管理
 * 评分和积分的不同：
 * 1. 评分是个百分制，主要评价各个方面的综合值
 * 2. 积分是个累计值，是各项活动的累计分，分数越大代表越好
 * @date 2019-09-09 17:15
 */
@Component
public class UserScoreHandler extends BaseHandler {

    @Autowired
    AuthService authService;

    @Autowired
    UserScoreService userScoreService;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    public UserScoreHandler() {
        super("用户评分");
    }

    public List<UserScoreDetail> getUseScore(Long userId, Date bussDay) {
        Date[] dates = this.getDays(bussDay);
        return this.getUseScore(userId, dates[0], dates[1]);
    }

    public List<UserScoreDetail> getUseScore(Long userId, String scoreGroup, Date bussDay) {
        Date[] dates = this.getDays(bussDay);
        return this.getUseScore(userId, scoreGroup, dates[0], dates[1]);
    }

    public Date[] getDays(Date bussDay) {
        Date endTime = DateUtil.getTodayTillMiddleNightDate(bussDay);
        int userScoreDays = systemConfigHandler.getIntegerConfig("user.score.days");
        Date startTime = DateUtil.getDate(0 - userScoreDays, bussDay);
        return new Date[]{startTime, endTime};
    }

    public List<UserScoreDetail> getUseScore(Long userId, Date startTime, Date endTime) {
        /**
         * todo 对于key，后期可以根据年龄或者不同阶段来设置的值，即可以分不同的组别
         * 比如：不同的年龄阶段可以用不同的评分标准
         */
        UserSetting us = authService.getUserSetting(userId);
        String scoreGroup = us.getScoreGroup();
        if (StringUtil.isEmpty(scoreGroup)) {
            scoreGroup = "0";
        }
        return this.getUseScore(userId, scoreGroup, startTime, endTime);
    }

    public List<UserScoreDetail> getUseScore(Long userId, String scoreGroup, Date startTime, Date endTime) {
        List<ScoreConfig> scList = userScoreService.selectActiveScoreConfigList(scoreGroup);
        List<UserScoreDetail> list = new ArrayList<>();
        int userScoreDays = systemConfigHandler.getIntegerConfig("user.score.days");
        for (ScoreConfig sc : scList) {
            double vv = userScoreService.getScoreValue(sc.getSqlContent(), userId, startTime, endTime);
            vv = NumberUtil.getDoubleValue(vv / userScoreDays, 2);
            //要除以天数，其实算的是每天的值
            int score = this.getScore(sc, vv);
            UserScoreDetail bean = new UserScoreDetail();
            bean.setScoreConfig(sc);
            bean.setStatValue(vv);
            bean.setScore(score);
            bean.setUserId(userId);
            bean.setCreatedTime(new Date());
            list.add(bean);
        }
        return list;
    }

    /**
     * 保存用户评分
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @param redo
     */
    public void saveUseScore(Long userId, Date startTime, Date endTime, boolean redo) {
        List<UserScoreDetail> list = this.getUseScore(userId, startTime, endTime);
        int totalScore = 0;
        for (UserScoreDetail usd : list) {
            totalScore += usd.getScore();
        }
        UserScore us = new UserScore();
        us.setUserId(userId);
        us.setCreatedTime(new Date());
        us.setStartTime(startTime);
        us.setEndTime(endTime);
        us.setScore(totalScore);
        userScoreService.saveUserScore(us, list, redo);
    }

    /**
     * 异步保存用户评分
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @param redo
     */
    @Async
    public void saveUseScoreAsync(Long userId, Date startTime, Date endTime, boolean redo) {
        this.saveUseScore(userId, startTime, endTime, redo);
    }

    private int getScore(ScoreConfig sc, double vv) {
        double limitValue = sc.getLimitValue();
        if (sc.getCompareType() == CompareType.MORE) {
            if (vv <= 0) {
                //0为0分
                return 0;
            } else if (vv >= limitValue) {
                //超过极限值为满分
                return sc.getMaxScore();
            } else {
                return (int) (vv / limitValue * sc.getMaxScore());
            }
        } else {
            if (vv <= 0) {
                //0为满分
                return sc.getMaxScore();
            } else if (vv >= limitValue) {
                //超过极限值为0分
                return 0;
            } else {
                return (int) ((limitValue - vv) / limitValue * sc.getMaxScore());
            }
        }
    }
}
