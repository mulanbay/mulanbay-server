package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.IPAddressUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.*;
import cn.mulanbay.pms.handler.bean.BudgetAmountBean;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.*;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.service.*;
import cn.mulanbay.pms.web.bean.LoginUser;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.UserCommonRequest;
import cn.mulanbay.pms.web.bean.request.auth.LoginRequest;
import cn.mulanbay.pms.web.bean.request.auth.RegisterRequest;
import cn.mulanbay.pms.web.bean.request.auth.UserSecAuthRequest;
import cn.mulanbay.pms.web.bean.request.buy.BuyRecordAnalyseStatSearch;
import cn.mulanbay.pms.web.bean.request.fund.BudgetSearch;
import cn.mulanbay.pms.web.bean.request.health.TreatRecordSearch;
import cn.mulanbay.pms.web.bean.request.music.MusicPracticeStatSearch;
import cn.mulanbay.pms.web.bean.request.sport.SportExerciseStatSearch;
import cn.mulanbay.pms.web.bean.request.user.UserGeneralStatSearch;
import cn.mulanbay.pms.web.bean.response.MyInfoResponse;
import cn.mulanbay.pms.web.bean.response.auth.RouterMetaVo;
import cn.mulanbay.pms.web.bean.response.auth.RouterVo;
import cn.mulanbay.pms.web.bean.response.auth.SecAuthInfoResponse;
import cn.mulanbay.pms.web.bean.response.user.UserGeneralLifeStatVo;
import cn.mulanbay.pms.web.bean.response.user.UserGeneralStatVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

/**
 * ????????????
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/main")
public class MainController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Value("${system.version}")
    private String version;

    @Value("${security.login.maxFail}")
    private int loginMaxFail;

    @Autowired
    AuthService authService;

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    UserCalendarService userCalendarService;

    @Autowired
    PmsNotifyHandler pmsNotifyHandler;

    @Autowired
    BuyRecordService buyRecordService;

    @Autowired
    SportExerciseService sportExerciseService;

    @Autowired
    MusicPracticeService musicPracticeService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    ReadingRecordService readingRecordService;

    @Autowired
    UserPlanService userPlanService;

    @Autowired
    BudgetHandler budgetHandler;

    @Autowired
    PmsMessageSendHandler pmsMessageSendHandler;

    @Autowired
    WxpayHandler wxpayHandler;

    @Autowired
    TokenHandler tokenHandler;

    /**
     * ??????
     *
     * @return
     */
    @RequestMapping(value = "/loginAuth", method = RequestMethod.POST)
    public ResultBean loginAuth(@RequestBody @Valid LoginRequest login) {
        //???????????????
        String verifyKey = CacheKey.getKey(CacheKey.CAPTCHA_CODE, login.getUuid());
        String serverCode = cacheHandler.getForString(verifyKey);
        if (StringUtil.isEmpty(serverCode) || !serverCode.equals(login.getCode())) {
            return callbackErrorCode(ErrorCode.USER_VERIFY_CODE_ERROR);
        }
        //??????????????????
        String username = login.getUsername();
        String failKey = CacheKey.getKey(CacheKey.USER_LOGIN_FAIL, username);
        Integer fails = cacheHandler.get(failKey, Integer.class);
        if (fails != null && fails >= loginMaxFail) {
            return callbackErrorCode(ErrorCode.USER_LOGIN_FAIL_MAX);
        }
        //????????????
        User user = authService.getUserByUsernameOrPhone(username);
        if (user == null) {
            return callbackErrorCode(ErrorCode.USER_NOTFOUND);
        } else {
            if (user.getStatus() == UserStatus.DISABLE) {
                return callbackErrorCode(ErrorCode.USER_IS_STOP);
            }
            if (user.getExpireTime() != null && user.getExpireTime().before(new Date())) {
                return callbackErrorCode(ErrorCode.USER_EXPIRED);
            }
            // ????????????
            String rp = user.getPassword();
            String encodePassword = tokenHandler.encodePassword(login.getPassword());
            if (!rp.equalsIgnoreCase(encodePassword)) {
                if (fails == null) {
                    fails = 1;
                } else {
                    fails++;
                }
                cacheHandler.set(failKey, fails, 300);
                return callbackErrorCode(ErrorCode.USER_PASSWORD_ERROR);
            }
            String token = doLogin(user, login.getFamilyMode());
            addLoginNotifyMsg(user.getId(), user.getUsername());
            Map map = new HashMap<>();
            map.put("token", token);
            return callback(map);
        }
    }

    private void addLoginNotifyMsg(Long userId, String username) {
        try {
            // ??????????????????
            pmsNotifyHandler.addMessageToNotifier(PmsErrorCode.USER_LOGIN, "??????????????????", "??????[" + username + "]????????????", new Date(), null);
            pmsNotifyHandler.addNotifyMessage(PmsErrorCode.USER_LOGIN, "??????????????????????????????", "????????????[" + username + "]????????????", userId, new Date());
        } catch (Exception e) {
            logger.error("??????????????????????????????", e);
        }

    }

    /**
     * ??????
     *
     * @param user
     */
    private String doLogin(User user, FamilyMode familyMode) {
        //??????????????????
        user.setLastLoginIp(IPAddressUtil.getIpAddress(request));
        user.setLastLoginTime(new Date());
        user.setLastFamilyMode(familyMode);
        LoginUser lu = tokenHandler.createLoginUser(user);
        String token = tokenHandler.createToken(lu);
        user.setLastLoginToken(lu.getLoginToken());
        baseService.updateObject(user);
        return token;
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping(value = "/secAuth", method = RequestMethod.POST)
    public ResultBean secAuth(@RequestBody @Valid UserSecAuthRequest sa) {
        User user = baseService.getObject(User.class, sa.getUserId());
        String serverAuthCode;
        if (user.getSecAuthType() == AuthType.PASSWORD) {
            serverAuthCode = user.getPassword();
        } else {
            String key = CacheKey.getKey(CacheKey.USER_SEC_AUTH_CODE, sa.getUserId().toString());
            //??????????????????????????????????????????
            serverAuthCode = cacheHandler.getForString(key);
            if (StringUtil.isEmpty(serverAuthCode)) {
                return callbackErrorCode(PmsErrorCode.USER_SEC_AUTH_CODE_NULL);
            }
        }
        String clientAuthCode = tokenHandler.encodePassword(sa.getAuthCode());
        if (!serverAuthCode.equals(clientAuthCode)) {
            return callbackErrorCode(PmsErrorCode.USER_SEC_AUTH_CODE_ERROR);
        }
        LoginUser lu = tokenHandler.createLoginUser(user);
        tokenHandler.verifyToken(lu);
        return callback(null);
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    @RequestMapping(value = "/sendSecAuthCode", method = RequestMethod.POST)
    public ResultBean sendSecAuthCode(UserCommonRequest sa) {
        User user = baseService.getObject(User.class, sa.getUserId());
        String serverAuthCode = null;
        boolean res = true;
        int expMin = 5;
        if (user.getSecAuthType() == AuthType.PASSWORD) {
            serverAuthCode = user.getPassword();
        } else {
            String code = NumberUtil.getRandNum(6);
            String title = "???????????????????????????";
            String content = "????????????????????????????????????" + code + ",???" + expMin + "???????????????";
            if (user.getSecAuthType() == AuthType.EMAIL) {
                res = pmsMessageSendHandler.sendMail(title, content, user.getEmail());
            } else if (user.getSecAuthType() == AuthType.WECHAT) {
                res = wxpayHandler.sendTemplateMessage(user.getId(), title, content, new Date(), LogLevel.NORMAL, null);
            }
            serverAuthCode = tokenHandler.encodePassword(code);
        }
        String key = CacheKey.getKey(CacheKey.USER_SEC_AUTH_CODE, sa.getUserId().toString());
        cacheHandler.set(key, serverAuthCode, expMin * 60);
        return callback(res);
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    @RequestMapping(value = "/getSecAuthInfo", method = RequestMethod.GET)
    public ResultBean getSecAuthInfo(UserCommonRequest sa) {
        User user = baseService.getObject(User.class, sa.getUserId());
        SecAuthInfoResponse res = new SecAuthInfoResponse();
        res.setSecAuthType(user.getSecAuthType());
        if (user.getSecAuthType() == AuthType.PASSWORD) {
            res.setAddress("?????????????????????");
        } else if (user.getSecAuthType() == AuthType.EMAIL) {
            res.setAddress(user.getEmail());
        } else if (user.getSecAuthType() == AuthType.WECHAT) {
            //todo ???????????????
            res.setAddress(user.getUsername());
        }
        return callback(res);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultBean logout(UserCommonRequest uc) {
        tokenHandler.deleteLoginUser(request);
        if (uc.getUserId() != null) {
            authService.deleteLastLoginToken(uc.getUserId());
        }
        return callback(null);
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
    public ResultBean myInfo(UserCommonRequest uc) {
        Long userId = uc.getUserId();
        User user = baseService.getObject(User.class, userId);
        MyInfoResponse res = new MyInfoResponse();
        res.setUsername(user.getUsername());
        res.setNickname(user.getNickname());
        res.setVersion(version);
        //??????????????????????????????????????????????????????
//        String key = MessageFormat.format(CacheKey.USER_TODAY_CALENDAR_COUNTS, userId);
//        Integer cc = cacheHandler.get(key, Integer.class);
//        if (cc == null) {
//            cc = userCalendarService.getTodayUserCalendarCount(userId).intValue();
//            cacheHandler.set(key, cc, 30);
//        }
        res.setTodayCalendars(0);
        return callback(res);
    }

    /**
     * ??????
     *
     * @return
     */
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ResultBean userRegister(@RequestBody @Valid RegisterRequest rr, HttpServletResponse response) {
        User user = new User();
        user.setCreatedTime(new Date());
        user.setUsername(rr.getUsername());
        user.setPassword(tokenHandler.encodePassword(rr.getPassword()));
        user.setLevel(3);
        user.setNickname(rr.getUsername());
        user.setPoints(0);
        UserSetting us = new UserSetting();
        us.setCreatedTime(new Date());
        us.setSendEmail(rr.getSendEmail());
        us.setSendWxMessage(rr.getSendWxMessage());
        us.setStatScore(true);
        authService.userRegister(user, us);
        //TODO ???????????????????????????????????????????????????????????????????????????
        //????????????
        doLogin(user, FamilyMode.P);
        return callback(null);
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping(value = "/generalStat", method = RequestMethod.GET)
    public ResultBean generalStat(@Valid UserGeneralStatSearch ugs) {
        UserGeneralStatVo res = new UserGeneralStatVo();
        BudgetSearch bs = new BudgetSearch();
        bs.setStatus(CommonStatus.ENABLE);
        bs.setUserId(ugs.getUserId());
        PageRequest pr = bs.buildQuery();
        pr.setBeanClass(Budget.class);
        //??????????????????????????????????????????
        List<Budget> budgetList = baseService.getBeanList(pr);
        if (budgetList.isEmpty()) {
            UserPlanConfigValue upcv = userPlanService.getMonthBudgetConfig(ugs.getUserId());
            //??????????????????(?????????????????????????????????)
            if (upcv != null) {
                res.setMonthBudget(Double.valueOf(upcv.getPlanValue()));
                res.setYearBudget(Double.valueOf(upcv.getPlanValue()) * 12);
            }
        } else {
            BudgetAmountBean bab = budgetHandler.calcBudgetAmount(budgetList, new Date());
            res.setMonthBudget(bab.getMonthBudget());
            res.setYearBudget(bab.getYearBudget());
        }
        BuyRecordAnalyseStatSearch sf = new BuyRecordAnalyseStatSearch();
        sf.setUserId(ugs.getUserId());
        sf.setStartDate(ugs.getStartDate());
        sf.setEndDate(ugs.getEndDate());
        sf.setConsumeType(ugs.getConsumeType());
        sf.setType(GroupType.TOTALPRICE);
        sf.setGroupField("goods_type_id");
        List<BuyRecordRealTimeStat> list = buyRecordService.getAnalyseStat(sf);
        for (BuyRecordRealTimeStat br : list) {
            res.appendConsume(br.getValue());
        }
        TreatRecordSearch trs = new TreatRecordSearch();
        trs.setStartDate(ugs.getStartDate());
        trs.setEndDate(ugs.getEndDate());
        trs.setUserId(ugs.getUserId());
        double treatAmount = budgetHandler.getTreadConsume(ugs.getStartDate(), ugs.getEndDate(), ugs.getUserId());
        BuyRecordRealTimeStat tt = new BuyRecordRealTimeStat();
        tt.setName("????????????");
        tt.setValue(treatAmount);
        list.add(tt);
        //?????????????????????
        res.setTotalTreatAmount(tt.getValue());
        res.setTotalTreatCount(0L);
        res.appendConsume(tt.getValue());

        //??????
        IncomeSummaryStat iss = incomeService.incomeSummaryStat(ugs.getUserId(), ugs.getStartDate(), ugs.getEndDate());
        res.setTotalIncome(iss.getTotalAmount() == null ? 0.0 : iss.getTotalAmount().doubleValue());

        //??????????????????
        int n = DateUtil.getMonthDays(new Date());
        int a = DateUtil.getDayOfMonth(new Date());
        res.setDayMonthRate(NumberUtil.getPercentValue(a, n, 2));
        res.setRemainMonthDays(n - a);
        res.setMonthDays(n);
        res.setMonthPassDays(a);

        Date[] dd = getStatDateRange(DateGroupType.MONTH, new Date());
        double monthConsumeAmount = buyRecordService.statBuyAmount(dd[0], dd[1], ugs.getUserId(), ugs.getConsumeType());
        res.appendMonthConsume(monthConsumeAmount);
        double treatMonthAmount = budgetHandler.getTreadConsume(dd[0], dd[1], ugs.getUserId());
        res.appendMonthConsume(treatMonthAmount);
        return callback(res);
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping(value = "/generalLifeStat", method = RequestMethod.GET)
    public ResultBean generalLifeStat(@Valid UserGeneralStatSearch ugs) {
        UserGeneralLifeStatVo res = new UserGeneralLifeStatVo();
        //???????????????
        SportExerciseStatSearch sess = new SportExerciseStatSearch();
        sess.setStartDate(ugs.getStartDate());
        sess.setEndDate(ugs.getEndDate());
        sess.setUserId(ugs.getUserId());
        SportExerciseStat eseData = sportExerciseService.statSportExercise(sess);
        res.setTotalSportExerciseCount(eseData.getTotalCount().longValue());
        res.setTotalSportExerciseHours(eseData.getTotalMinutes() == null ? 0.0 : eseData.getTotalMinutes().doubleValue() / 60);

        //??????????????????
        MusicPracticeStatSearch mpss = new MusicPracticeStatSearch();
        mpss.setStartDate(ugs.getStartDate());
        mpss.setEndDate(ugs.getEndDate());
        mpss.setUserId(ugs.getUserId());
        MusicPracticeSummaryStat mpssData = musicPracticeService.musicPracticeSummaryStat(mpss);
        res.setTotalMusicPracticeCount(mpssData.getTotalCount().longValue());
        res.setTotalMusicPracticeHours(mpssData.getTotalMinutes() == null ? 0.0 : mpssData.getTotalMinutes().doubleValue() / 60);

        //????????????
        ReadingDetailSummaryStat rdss = readingRecordService.statReadingDetailSummary(sess.getStartDate(), sess.getEndDate(), sess.getUserId());
        res.setTotalReadingCount(rdss.getTotalCount().longValue());
        res.setTotalReadingHours(rdss.getTotalMinutes() == null ? 0 : rdss.getTotalMinutes().doubleValue() / 60);
        return callback(res);
    }


    /**
     * ?????????
     *
     * @return
     */
    @RequestMapping(value = "/getRouters", method = RequestMethod.GET)
    public ResultBean getRouters(UserCommonRequest ucr) {
        LoginUser loginUser = tokenHandler.getLoginUser(request);
        Long roleId = loginUser.getRoleId();
        List<SystemFunction> sfList = authService.selectRoleFunctionMenuList(roleId, null);
        List<SystemFunction> funcTree = this.getFunctionTree(sfList, 0L);
        return callback(buildMenus(funcTree));
    }

    /**
     * ????????????RuoYi???????????????
     *
     * @param menus
     * @return
     */
    private List<RouterVo> buildMenus(List<SystemFunction> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SystemFunction sf : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(sf.getVisible().booleanValue() == true ? false : true);
            router.setName(getRouteName(sf));
            router.setPath(getRouterPath(sf));
            router.setComponent(getComponent(sf));
            router.setMeta(new RouterMetaVo(sf.getName(), sf.getImageName(),!sf.getCache()));
            List<SystemFunction> cMenus = sf.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && FunctionDataType.M.equals(sf.getFunctionDataType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(sf)) {
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(sf.getPath());
                children.setComponent(sf.getComponent());
                children.setName(StringUtils.capitalize(sf.getPath()));
                children.setMeta(new RouterMetaVo(sf.getName(), sf.getImageName(),!sf.getCache()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    private List<SystemFunction> getFunctionTree(List<SystemFunction> list, long pid) {
        List<SystemFunction> res = new ArrayList<>();
        for (SystemFunction sf : list) {
            if (sf.getParentId() == pid) {
                res.add(sf);
                List<SystemFunction> children = getFunctionTree(list, sf.getId().longValue());
                sf.setChildren(children);
            }
        }
        return res;
    }

    /**
     * ??????????????????
     * ???????????????path????????????/,???????????????vue??????????????????????????????/
     * ??????path???buyRecord/dateStat,???name:BuyRecordDateStat
     * @param menu ????????????
     * @return ????????????
     */
    public String getRouteName(SystemFunction menu) {
        String path = menu.getPath();
        String[] ss = path.split("/");
        String routerName="";
        for(String s : ss){
            routerName += StringUtils.capitalize(s);
        }
        // ???????????????????????????????????????????????????
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }

        return routerName;
    }

    /**
     * ??????????????????
     *
     * @param menu ????????????
     * @return ????????????
     */
    public String getRouterPath(SystemFunction menu) {
        String routerPath = menu.getPath();
        // ???????????????????????????????????????????????????
        if (0 == menu.getParentId().intValue() && FunctionDataType.M.equals(menu.getFunctionDataType())
                && (false == menu.getFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // ???????????????????????????????????????????????????
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * ???????????????????????????
     *
     * @param menu ????????????
     * @return ??????
     */
    public boolean isMenuFrame(SystemFunction menu) {
        //???????????????????????????&& FunctionDataType.C.equals(menu.getFunctionDataType())
        return menu.getParentId().intValue() == 0
                && (false == menu.getFrame());
    }

    /**
     * ??????????????????
     *
     * @param menu ????????????
     * @return ????????????
     */
    public String getComponent(SystemFunction menu) {
        String component = "Layout";
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        }
        return component;
    }

}
