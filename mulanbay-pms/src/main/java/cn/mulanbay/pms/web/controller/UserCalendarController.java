package cn.mulanbay.pms.web.controller;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.queue.LimitQueue;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.handler.UserCalendarHandler;
import cn.mulanbay.pms.handler.bean.UserCalendarCountsBean;
import cn.mulanbay.pms.handler.bean.UserCalendarIdBean;
import cn.mulanbay.pms.persistent.domain.UserCalendar;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.enums.UserCalendarFinishType;
import cn.mulanbay.pms.persistent.enums.UserCalendarSource;
import cn.mulanbay.pms.persistent.service.UserCalendarService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.UserCommonRequest;
import cn.mulanbay.pms.web.bean.request.usercalendar.*;
import cn.mulanbay.pms.web.bean.response.calendar.UserCalendarVo;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户日历
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userCalendar")
public class UserCalendarController extends BaseController {

    private static Class<UserCalendar> beanClass = UserCalendar.class;

    @Autowired
    UserCalendarService userCalendarService;

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    UserCalendarHandler userCalendarHandler;

    @Autowired
    PmsNotifyHandler pmsNotifyHandler;

    /**
     * 获取列表数据
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserCalendarSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("finishType", Sort.ASC);
        pr.addSort(s);
        Sort s2 = new Sort("expireTime", Sort.DESC);
        pr.addSort(s2);
        PageResult<UserCalendar> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 我的日历页面使用
     *
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResultBean getList(UserCalendarListSearch sf) {
        List<UserCalendarVo> res = userCalendarHandler.getUserCalendarList(sf);
        return callback(res);
    }


    /**
     * 发送消息
     *
     * @return
     */
    @RequestMapping(value = "/sendCalendarMessage", method = RequestMethod.POST)
    public ResultBean sendCalendarMessage(@RequestBody @Valid SendCalendarMessageRequest scmr) {
        UserCalendarListSearch sf = new UserCalendarListSearch();
        Date start = DateUtil.getDate(scmr.getDate(), DateUtil.FormatDay1);
        sf.setStartDate(start);
        sf.setEndDate(DateUtil.getTodayTillMiddleNightDate(scmr.getDate()));
        sf.setUserId(scmr.getUserId());
        sf.setNeedFinished(false);
        sf.setNeedPeriod(true);
        sf.setNeedBudget(true);
        sf.setNeedTreatDrug(true);
        sf.setNeedBandLog(false);
        List<UserCalendarVo> ucList = userCalendarHandler.getUserCalendarList(sf);
        //过滤掉不适合的
        List<UserCalendar> res = new ArrayList<>();
        int sn = DateUtil.getDayOfYear(start);
        for (UserCalendarVo vo : ucList) {
            int cn = DateUtil.getDayOfYear(vo.getBussDay());
            if (sn == cn) {
                UserCalendar uc = new UserCalendar();
                BeanCopy.copyProperties(vo,uc);
                UserCalendarIdBean idBean = userCalendarHandler.parseId(vo.getId());
                uc.setId(idBean.getId());
                res.add(uc);
            }
        }
        Date now = new Date();
        int n = res.size();
        //int tp = (int)Math.ceil(((n+0.0)/5));
        String title = scmr.getDate();
        int bd = DateUtil.getIntervalDays(start, now);
        if (bd == 0) {
            title += "(今日)";
        }
        title += "日历行程[共" + n + "条]";
        //每条消息的日历数
        int p = 5;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            UserCalendar uc = res.get(i);
            sb.append((i + 1) + ".");
            sb.append(uc.getTitle());
            if (false == uc.getAllDay()) {
                sb.append("[" + DateUtil.getFormatDate(uc.getBussDay(), "HH:mm") + "]");
            }
            sb.append("\n");
            if ((i + 1) % p == 0 || i == n - 1) {
                //发送消息
                pmsNotifyHandler.addNotifyMessage(PmsErrorCode.CALENDAR_MESSAGE_NOTIFY, title, sb.toString(), scmr.getUserId(), now);
                sb = new StringBuffer();
            }
        }
        return callback(null);
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserCalendarFormRequest formRequest) {
        UserCalendar bean = new UserCalendar();
        BeanCopy.copyProperties(formRequest, bean);
        checkUserCalendarData(bean);
        bean.setCreatedTime(new Date());
        bean.setDelayCounts(0);
        bean.setReadOnly(false);
        baseService.saveObject(bean);
        return callback(null);
    }

    /**
     * 创建(我的日历页面进入，有tui.calendar编辑页面来的)
     *
     * @return
     */
    @RequestMapping(value = "/create2", method = RequestMethod.POST)
    public ResultBean create2(@RequestBody @Valid UserCalendarCreate2Request formRequest) {
        UserCalendar bean = new UserCalendar();
        BeanCopy.copyProperties(formRequest, bean);
        checkUserCalendarData(bean);
        bean.setContent(bean.getTitle());
        bean.setSourceType(formRequest.getCalendarId());
        String bussIdentityKey = "C2_" + DateUtil.getFormatDate(new Date(), DateUtil.Format24Datetime2);
        bean.setBussIdentityKey(bussIdentityKey);
        bean.setReadOnly(false);
        bean.setCreatedTime(new Date());
        bean.setDelayCounts(0);
        bean.setPeriod(PeriodType.ONCE);
        baseService.saveObject(bean);
        return callback(null);
    }

    private void checkUserCalendarData(UserCalendar bean) {
        PeriodType period = bean.getPeriod();
        //默认第一天
        if (period == PeriodType.WEEKLY || period == PeriodType.MONTHLY) {
            if (StringUtil.isEmpty(bean.getPeriodValues())) {
                bean.setPeriodValues("1");
            }
        }
        if (bean.getExpireTime() == null) {
            if (period == PeriodType.ONCE) {
                bean.setExpireTime(DateUtil.getTodayTillMiddleNightDate(new Date()));
            } else {
                //设置最大值
                bean.setExpireTime(DateUtil.getDate("2099-12-31 23:59:59", DateUtil.Format24Datetime));
            }

        }
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest getRequest) {
        UserCalendar bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 以日历的来源查找
     *
     * @return
     */
    @RequestMapping(value = "/getBySource", method = RequestMethod.GET)
    public ResultBean getBySource(@Valid UserCalendarGetBySourceRequest getRequest) {
        UserCalendar bean = userCalendarService.getUserCalendar(getRequest.getUserId(),getRequest.getSourceType(),getRequest.getSourceId());
        return callback(bean);
    }

    /**
     * 查找源
     *
     * @return
     */
    @RequestMapping(value = "/getSource", method = RequestMethod.GET)
    public ResultBean getSource(@Valid UserCalendarGetSourceRequest getRequest) {
        UserCalendarIdBean bean = userCalendarHandler.parseId(getRequest.getId());
        Long sourceId = bean.getId();
        UserCalendarSource source = bean.getSource();
        Object res = baseService.getObject(source.getBeanClass(),sourceId);
        return callback(res);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserCalendarFormRequest formRequest) {
        UserCalendar bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        checkUserCalendarData(bean);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
    }

    /**
     * 修改(日历视图)
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultBean update(@RequestBody @Valid UserCalendarUpdateRequest formRequest) {
        UserCalendar bean = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        BeanCopy.copyProperties(formRequest, bean);
        UserCalendarSource ucs = UserCalendarSource.getUserCalendarSource(formRequest.getCalendarId());
        bean.setSourceType(ucs);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
    }

    /**
     * 今日行程
     *
     * @return
     */
    @RequestMapping(value = "/todayCalendarList")
    public ResultBean todayCalendarList() {
        List<UserCalendar> list = userCalendarService.getCurrentUserCalendarList(this.getCurrentUserId());
        return callback(list);
    }

    /**
     * 完成今日行程
     *
     * @return
     */
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public ResultBean finish(@RequestBody @Valid CommonBeanGetRequest getRequest) {
        UserCalendar userCalendar = baseService.getObjectWithUser(beanClass, getRequest.getId(), getRequest.getUserId());
        if (userCalendar.getFinishedTime() != null) {
            return callbackErrorInfo("日程无法被重复完成。");
        }
        Date date = new Date();
        userCalendar.setFinishedTime(date);
        userCalendar.setLastModifyTime(date);
        userCalendar.setExpireTime(date);
        userCalendar.setFinishType(UserCalendarFinishType.MANUAL);
        baseService.updateObject(userCalendar);
        //删除缓存ß
        String key = MessageFormat.format(CacheKey.USER_TODAY_CALENDAR_COUNTS, getRequest.getUserId());
        cacheHandler.delete(key);
        return callback(null);
    }

    /**
     * 重开
     *
     * @return
     */
    @RequestMapping(value = "/reOpen", method = RequestMethod.POST)
    public ResultBean reOpen(@RequestBody @Valid CommonBeanGetRequest getRequest) {
        UserCalendar userCalendar = baseService.getObjectWithUser(beanClass, getRequest.getId(), getRequest.getUserId());
        Date date = new Date();
        userCalendar.setFinishedTime(null);
        userCalendar.setLastModifyTime(date);
        userCalendar.setExpireTime(date);
        userCalendar.setBussDay(date);
        userCalendar.setFinishType(null);
        baseService.updateObject(userCalendar);
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

    /**
     * 今日任务数
     * @param uc
     * @return
     */
    @RequestMapping(value = "/dailyCount", method = RequestMethod.GET)
    public ResultBean dailyCount(UserCommonRequest uc) {
        Long userId = uc.getUserId();
        String key = MessageFormat.format(CacheKey.USER_TODAY_CALENDAR_COUNTS, userId);
        Integer cc = cacheHandler.get(key, Integer.class);
        if (cc == null) {
            cc = userCalendarService.getTodayUserCalendarCount(userId).intValue();
            cacheHandler.set(key, cc, 30);
        }
        return callback(cc);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/dailyCountStat", method = RequestMethod.GET)
    public ResultBean dailyCountStat() {
        Long userId = this.getCurrentUserId();
        String key = CacheKey.getKey(CacheKey.USER_TODAY_CALENDAR_TIMELINE_COUNTS, userId.toString());
        LimitQueue<UserCalendarCountsBean> queue = cacheHandler.get(key, LimitQueue.class);
        if (queue == null) {
            return callbackErrorInfo("没有相关统计数据");
        }
        ChartData chartData = new ChartData();
        chartData.setTitle("每日任务量");
        chartData.setLegendData(new String[]{"任务数(个)"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("任务数(个)");
        int n = queue.size();
        for (int i = 0; i < n; i++) {
            UserCalendarCountsBean sd = queue.get(i);
            chartData.getXdata().add(DateUtil.getFormatDate(sd.getDate(), "MM-dd"));
            yData1.getData().add(sd.getCounts());
        }
        chartData.getYdata().add(yData1);
        return callback(chartData);
    }
}
