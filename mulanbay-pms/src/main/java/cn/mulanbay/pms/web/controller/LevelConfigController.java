package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.persistent.domain.LevelConfig;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.domain.UserRewardPointRecord;
import cn.mulanbay.pms.persistent.domain.UserScore;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.LevelService;
import cn.mulanbay.pms.persistent.service.UserScoreService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.data.JudgeLevelRequest;
import cn.mulanbay.pms.web.bean.request.data.LevelConfigFormRequest;
import cn.mulanbay.pms.web.bean.request.data.LevelConfigSearch;
import cn.mulanbay.pms.web.bean.request.data.SelfJudgeLevelRequest;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户等级
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/levelConfig")
public class LevelConfigController extends BaseController {

    private static Class<LevelConfig> beanClass = LevelConfig.class;

    @Autowired
    AuthService authService;

    @Autowired
    LevelService levelService;

    @Autowired
    UserScoreService userScoreService;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(LevelConfigSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("level", Sort.DESC);
        pr.addSort(sort);
        PageResult<LevelConfig> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid LevelConfigFormRequest formRequest) {
        LevelConfig bean = new LevelConfig();
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        LevelConfig bean = baseService.getObject(beanClass, getRequest.getId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid LevelConfigFormRequest formRequest) {
        LevelConfig bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        baseService.deleteObjects(beanClass, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

    /**
     * 用户自评
     *
     * @return
     */
    @RequestMapping(value = "/selfJudge", method = RequestMethod.POST)
    public ResultBean selfJudge(@RequestBody @Valid SelfJudgeLevelRequest jlr) {
        User user = baseService.getObject(User.class, jlr.getUserId());
        return this.judge(user, jlr.getUpdateLevel());
    }

    /**
     * 评定等级
     *
     * @return
     */
    @RequestMapping(value = "/judgeLevel", method = RequestMethod.POST)
    public ResultBean judgeLevel(@RequestBody @Valid JudgeLevelRequest jlr) {
        User user = authService.getUserByUsernameOrPhone(jlr.getUsername());
        if (user == null) {
            return callbackErrorCode(ErrorCode.USER_NOTFOUND);
        }
        return this.judge(user, jlr.getUpdateLevel());
    }

    /**
     * 判定
     *
     * @param user
     * @param updateLevel
     * @return
     */
    private ResultBean judge(User user, boolean updateLevel) {
        UserScore us = userScoreService.getLatestScore(user.getId());
        if (us == null) {
            return callbackErrorCode(PmsErrorCode.USER_SCORE_NULL);
        }
        LevelConfig preLevel = levelService.getPreJudgeLevel(us.getScore(), user.getPoints());
        if (preLevel == null) {
            return callbackErrorCode(PmsErrorCode.USER_PRE_SCORE_NULL);
        }
        LevelConfig current = levelService.getLevelConfig(user.getLevel());
        LevelConfig lc = this.matchLevel(preLevel.getLevel(), user.getId());
        if (lc != null && true == updateLevel && current.getLevel().intValue() != lc.getLevel().intValue()) {
            user.setLevel(lc.getLevel());
            user.setLastModifyTime(new Date());
            baseService.updateObject(user);
        }
        Map res = new HashMap<>();
        res.put("newLevel", lc);
        res.put("currentLevel", current);
        return callback(res);
    }

    private LevelConfig matchLevel(int maxLevel, Long userId) {
        int days = systemConfigHandler.getIntegerConfig("user.level.maxCompareDays");
        List<UserScore> usList = userScoreService.getList(userId, days);
        List<UserRewardPointRecord> urList = authService.getUserRewardPointRecordList(userId, days);
        for (int i = maxLevel; i >= 1; i--) {
            LevelConfig lc = levelService.getLevelConfig(i);
            boolean scoreMatch = true;
            //没有足够数据直接判定为false
            if (lc.getScoreDays() <= usList.size()) {
                for (int j = 0; j < lc.getScoreDays(); j++) {
                    UserScore s = usList.get(j);
                    if (s.getScore() < lc.getScore()) {
                        scoreMatch = false;
                        break;
                    }
                }
            } else {
                scoreMatch = false;
            }

            boolean pointsMatch = true;
            //没有足够数据直接判定为false
            if (lc.getPointsDays() <= urList.size()) {
                for (int j = 0; j < lc.getPointsDays(); j++) {
                    UserRewardPointRecord r = urList.get(j);
                    if (r.getAfterPoints() < lc.getPoints()) {
                        pointsMatch = false;
                        break;
                    }
                }
            } else {
                pointsMatch = false;
            }
            if (scoreMatch && pointsMatch) {
                //两个都匹配
                return lc;
            }
        }
        return null;
    }
}
