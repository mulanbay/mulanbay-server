package cn.mulanbay.pms.web.controller;

import cn.mulanbay.business.handler.CacheHandler;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.*;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.PmsScheduleHandler;
import cn.mulanbay.pms.handler.SharesHandler;
import cn.mulanbay.pms.handler.bean.SharesIndexBean;
import cn.mulanbay.pms.handler.bean.SharesMonitorBean;
import cn.mulanbay.pms.handler.bean.SharesPriceBean;
import cn.mulanbay.pms.handler.bean.SharesScoreConfig;
import cn.mulanbay.pms.handler.job.SharesMonitorJobPara;
import cn.mulanbay.pms.persistent.domain.*;
import cn.mulanbay.pms.persistent.dto.SharesPriceAnalyseStat;
import cn.mulanbay.pms.persistent.dto.UserSharesWarnDataStat;
import cn.mulanbay.pms.persistent.dto.UserSharesWarnDateStat;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.enums.SharesWarnType;
import cn.mulanbay.pms.persistent.service.PmsScheduleService;
import cn.mulanbay.pms.persistent.service.SharesService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.UserCommonRequest;
import cn.mulanbay.pms.web.bean.request.buy.BuyTypeDeleteRequest;
import cn.mulanbay.pms.web.bean.request.fund.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.fund.UserSharesDetailVo;
import cn.mulanbay.pms.web.bean.response.fund.UserSharesWarnDetailVo;
import cn.mulanbay.pms.web.bean.response.fund.UserSharesWarnVo;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * 用户股票
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userShares")
public class UserSharesController extends BaseController {

    private static Class<UserShares> beanClass = UserShares.class;

    //股票监控的调度ID
    @Value("${shares.monitor.taskTriggerId}")
    private Long smTaskTriggerId;

    //股票统计的调度ID
    @Value("${shares.stat.taskTriggerId}")
    private Long ssTaskTriggerId;

    @Autowired
    SharesHandler sharesHandler;

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    SharesService sharesService;

    @Autowired
    PmsScheduleService pmsScheduleService;

    @Autowired
    PmsScheduleHandler pmsScheduleHandler;

    /**
     * 获取用户股票树
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getUserSharesTree")
    public ResultBean getUserSharesTree(UserSharesSearch sf) {
        try {
            sf.setStatus(CommonStatus.ENABLE);
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            Sort sort = new Sort("createdTime", Sort.ASC);
            pr.addSort(sort);
            PageResult<UserShares> qr = baseService.getBeanResult(pr);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<UserShares> gtList = qr.getBeanList();
            TreeBean shTb = new TreeBean();
            shTb.setId("-1");
            shTb.setText("沪市");
            TreeBean szTb = new TreeBean();
            szTb.setId("-2");
            szTb.setText("深市");
            list.add(shTb);
            list.add(szTb);
            for (UserShares gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                if (gt.getCode().startsWith("sh")) {
                    shTb.addChild(tb);
                } else {
                    szTb.addChild(tb);
                }
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户股票树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserSharesSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("createdTime", Sort.ASC);
        pr.addSort(sort);
        PageResult<UserShares> qr = baseService.getBeanResult(pr);
        List<UserSharesDetailVo> newList = new ArrayList<>();
        long now = System.currentTimeMillis();
        for (UserShares us : qr.getBeanList()) {
            UserSharesDetailVo usd = new UserSharesDetailVo();
            BeanCopy.copyProperties(us, usd);
            SharesPriceBean spb = sharesHandler.getSharesPrice(us.getCode());
            usd.setCurrentPrice(spb.getCurrentPrice());
            if (spb.getOccurTime() != null) {
                usd.setPriceGetFromNow((now - spb.getOccurTime().getTime()) / 1000);
            }
            //获取价格监控
            String key = CacheKey.getKey(CacheKey.SHARES_PRICE_MONITOR, spb.getCode());
            SharesMonitorBean smb = cacheHandler.get(key, SharesMonitorBean.class);
            usd.setSmb(smb);
            newList.add(usd);
        }
        PageResult<UserSharesDetailVo> result = new PageResult<>();
        result.setBeanList(newList);
        result.setMaxRow(qr.getMaxRow());
        result.setPageSize(qr.getPageSize());
        return callbackDataGrid(result);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserSharesFormRequest bean) {
        SharesPriceBean spb = sharesHandler.getSharesPrice(bean.getCode());
        if (spb.getCurrentPrice() < 0) {
            return callbackErrorCode(PmsErrorCode.SHARES_CODE_NOT_FOUND);
        }
        UserShares userShares = new UserShares();
        BeanCopy.copyProperties(bean, userShares);
        userShares.setCreatedTime(new Date());
        baseService.saveObject(userShares);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest gr) {
        if (gr.getId() < 0) {
            UserShares userShares = baseService.getObject(beanClass, gr.getId());
            SharesIndexBean sip = sharesHandler.getIndexPrice(userShares.getCode());
            return callback(sip);
        } else {
            UserShares userShares = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
            SharesPriceBean spb = sharesHandler.getSharesPrice(userShares.getCode());
            UserSharesDetailVo usd = new UserSharesDetailVo();
            BeanCopy.copyProperties(userShares, usd);
            usd.setCurrentPrice(spb.getCurrentPrice());
            return callback(usd);
        }
    }

    /**
     * 获取当前股票价格
     *
     * @return
     */
    @RequestMapping(value = "/getCurrentPrice", method = RequestMethod.GET)
    public ResultBean getCurrentPrice(String code) {
        SharesPriceBean spb = sharesHandler.getSharesPrice(code);
        if (spb.getCurrentPrice() < 0) {
            return callbackErrorCode(PmsErrorCode.SHARES_CODE_NOT_FOUND);
        }
        return callback(spb);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserSharesFormRequest bean) {
        SharesPriceBean spb = sharesHandler.getSharesPrice(bean.getCode());
        if (spb.getCurrentPrice() < 0) {
            return callbackErrorCode(PmsErrorCode.SHARES_CODE_NOT_FOUND);
        }
        UserShares userShares = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        BeanCopy.copyProperties(bean, userShares);
        userShares.setLastModifyTime(new Date());
        baseService.updateObject(userShares);
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid BuyTypeDeleteRequest bdr) {
        this.deleteUserEntity(beanClass, NumberUtil.stringArrayToLongArray(bdr.getIds().split(",")), bdr.getUserId());
        return callback(null);
    }

    /**
     * 重新计算价格统计
     *
     * @return
     */
    @RequestMapping(value = "/resetPriceStat", method = RequestMethod.POST)
    public ResultBean resetPriceStat(@RequestBody @Valid CommonBeanGetRequest gr) {
        UserShares userShares = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        SharesPriceBean spb = sharesHandler.getSharesPrice(userShares.getCode());
        SharesMonitorBean smb = null;
        ResetUserSharesPriceSearch sf = new ResetUserSharesPriceSearch();
        sf.setCode(userShares.getCode());
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(SharesPrice.class);
        Sort s = new Sort("occurTime", Sort.ASC);
        pr.addSort(s);
        PageResult<SharesPrice> qr = baseService.getBeanResult(pr);
        int n = qr.getBeanList().size();
        if (n == 0) {
            return callback(null);
        }
        SharesMonitorJobPara umc = this.getConfig();
        for (int i = 0; i < n; i++) {
            SharesPrice sp = qr.getBeanList().get(i);
            smb = sharesHandler.calculatePriceChange(smb, sp.getCurrentPrice(), sp.getOccurTime(), sp.getCode(), umc.getFailPercent(), umc.getGainPercent());
        }
        //直接更新替换缓存
        String key = CacheKey.getKey(CacheKey.SHARES_PRICE_MONITOR, spb.getCode());
        cacheHandler.set(key, smb, umc.getCacheHours() * 3600);
        //更新分数
        UserSharesScore ssb = sharesHandler.calculateScore(spb, userShares, smb);
        sharesService.updateUserSharesScore(userShares.getId(), ssb.getTotalScore());
        return callback(null);
    }

    /**
     * 获取监控配置
     *
     * @return
     */
    @RequestMapping(value = "/getMonitorConfig", method = RequestMethod.GET)
    public ResultBean getMonitorConfig() {
        return callback(getConfig());
    }

    private SharesMonitorJobPara getConfig() {
        TaskTrigger bean = baseService.getObject(TaskTrigger.class, smTaskTriggerId);
        String triggerPara = bean.getTriggerParas();
        SharesMonitorJobPara para = (SharesMonitorJobPara) JsonUtil.jsonToBean(triggerPara, SharesMonitorJobPara.class);
        if (para == null) {
            para = new SharesMonitorJobPara();
        }
        return para;
    }

    /**
     * 统计股票（发送微信消息）
     *
     * @return
     */
    @RequestMapping(value = "/statShares", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean statShares(UserCommonRequest ucr) {
        Long userId = ucr.getUserId();
        pmsScheduleHandler.manualNew(ssTaskTriggerId, new Date(), false, userId, "由用户手动触发");
        return callback(null);
    }

    /**
     * 更新监控配置
     *
     * @return
     */
    @RequestMapping(value = "/updateMonitorConfig", method = RequestMethod.POST)
    public ResultBean updateMonitorConfig(@RequestBody @Valid SharesMonitorJobPara bean) {
        // 更新调度
        pmsScheduleService.updateTaskTriggerPara(smTaskTriggerId, JsonUtil.beanToJson(bean));
        return callback(null);
    }

    /**
     * 分析
     *
     * @return
     */
    @RequestMapping(value = "/analyse", method = RequestMethod.GET)
    public ResultBean analyse(@Valid UserSharesPriceAnalyseSearch sf) {
        if (sf.getUserSharesId() < 0) {
            //大盘数据
            UserShares userShares = baseService.getObject(beanClass, sf.getUserSharesId());
            IndexSharesPriceAnalyseSearch ispa = new IndexSharesPriceAnalyseSearch();
            ispa.setCode(userShares.getCode());
            BeanCopy.copyProperties(sf, ispa);
            return this.indexAnalyse(ispa);
        }
        UserShares userShares = this.getUserEntity(beanClass, sf.getUserSharesId(), sf.getUserId());
        sf.setCode(userShares.getCode());
        ChartData chartData = new ChartData();
        chartData.setTitle("股票价格变化");
        chartData.setLegendData(new String[]{"当前价(元)", "价格浮动比例(%)", "价格浮动值(元)", "股票评分", "换手率(%)"});
        ChartYData yData = new ChartYData();
        yData.setName("当前价(元)");
        ChartYData y2Data = new ChartYData();
        y2Data.setName("价格浮动比例(%)");
        ChartYData y3Data = new ChartYData();
        y3Data.setName("价格浮动值(元)");
        ChartYData y4Data = new ChartYData();
        y4Data.setName("股票评分");
        ChartYData y5Data = new ChartYData();
        y5Data.setName("换手率(%)");
        List<SharesPriceAnalyseStat> list = sharesService.statSharesPriceAnalyse(sf);
        int n = list.size();
        if (n > 0) {
            double lastPrice = list.get(0).getCurrentPrice().doubleValue();
            for (int i = 0; i < n; i++) {
                SharesPriceAnalyseStat bean = list.get(i);
                chartData.getXdata().add(DateUtil.getFormatDate(bean.getOccurTime(), DateUtil.Format24Datetime));
                yData.getData().add(bean.getCurrentPrice());
                double fm = bean.getCurrentPrice().doubleValue() - lastPrice;
                double percent = fm / lastPrice * 100.0;
                y2Data.getData().add(PriceUtil.changeToString(2, percent));
                y3Data.getData().add(PriceUtil.changeToString(2, fm));
                lastPrice = bean.getCurrentPrice().doubleValue();
                y4Data.getData().add(bean.getTotalScore());
                y5Data.getData().add(bean.getTurnOver() == null ? 0 : bean.getTurnOver());

            }
        }
        String subTitle = "股票[" + userShares.getName() + "]";
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(yData);
        chartData.getYdata().add(y2Data);
        chartData.getYdata().add(y3Data);
        chartData.getYdata().add(y4Data);
        chartData.getYdata().add(y5Data);
        return callback(chartData);
    }

    /**
     * 大盘分析
     *
     * @return
     */
    @RequestMapping(value = "/indexAnalyse", method = RequestMethod.GET)
    public ResultBean indexAnalyse(@Valid IndexSharesPriceAnalyseSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle("大盘分析");
        chartData.setLegendData(new String[]{"指数", "当前价(元)", "涨跌率", "成交量(手)", "成交额(万元)"});
        ChartYData yData = new ChartYData();
        yData.setName("指数");
        ChartYData y2Data = new ChartYData();
        y2Data.setName("当前价(元)");
        ChartYData y3Data = new ChartYData();
        y3Data.setName("涨跌率");
        ChartYData y4Data = new ChartYData();
        y4Data.setName("成交量(手)");
        ChartYData y5Data = new ChartYData();
        y5Data.setName("成交额(万元)");
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(SharesIndexPrice.class);
        Sort sort = new Sort("occurTime", Sort.ASC);
        pr.addSort(sort);
        List<SharesIndexPrice> list = baseService.getBeanList(pr);
        int n = list.size();
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                SharesIndexPrice bean = list.get(i);
                chartData.getXdata().add(DateUtil.getFormatDate(bean.getOccurTime(), DateUtil.Format24Datetime));
                yData.getData().add(bean.getPoint());
                y2Data.getData().add(bean.getCurrentPrice());
                y3Data.getData().add(bean.getFgRate());
                y4Data.getData().add(bean.getDeals());
                y5Data.getData().add(bean.getDealAmount());
            }
        }
        String subTitle = "大盘";
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(yData);
        chartData.getYdata().add(y2Data);
        chartData.getYdata().add(y3Data);
        chartData.getYdata().add(y4Data);
        chartData.getYdata().add(y5Data);
        return callback(chartData);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(UserCommonRequest ucr) {
        Long userId = ucr.getUserId();
        List<UserShares> list = sharesService.getActiveUserShares(userId);
        //原始资产
        double op = 0;
        //当前资产
        double cp = 0;
        for (UserShares us : list) {
            op += us.getBuyPrice() * us.getShares();
            SharesPriceBean spb = sharesHandler.getSharesPrice(us.getCode());
            cp += spb.getCurrentPrice() * us.getShares();
        }
        Map res = new HashMap<>();
        res.put("op", op);
        res.put("cp", cp);
        return callback(res);
    }

    /**
     * 获取评分
     *
     * @return
     */
    @RequestMapping(value = "/getScoreStat", method = RequestMethod.GET)
    public ResultBean getScoreStat(@Valid UserSharesScoreStatRequest gr) {
        UserShares userShares = this.getUserEntity(beanClass, gr.getUserSharesId(), gr.getUserId());
        UserSharesScore uss = sharesService.getUserSharesScore(gr.getUserSharesId(), gr.getUserId(), gr.getId(), gr.getType());
        if (uss == null) {
            return callbackErrorInfo("无法找到相关评分记录");
        }
        ChartRadarData chartRadarData = new ChartRadarData();
        chartRadarData.setTitle("股票[" + userShares.getName() + "]评分分析");
        chartRadarData.setSubTitle("股票[" + userShares.getName() + "],代码[" + userShares.getCode() + "]");
        chartRadarData.getLegendData().add("得分");
        UserSharesScoreConfig ussc = sharesService.getUserSharesScoreConfig(gr.getUserId());
        if (ussc == null) {
            ussc = new UserSharesScoreConfig();
        }
        SharesScoreConfig ssc = SharesScoreConfig.change(ussc);

        chartRadarData.addIndicatorData("价格分", ssc.getMaxPriceScore() + ssc.getMaxCbPriceScore());
        chartRadarData.addIndicatorData("资产分", ssc.getMaxAssetScore());
        chartRadarData.addIndicatorData("涨跌分", ssc.getMaxFgScore());
        chartRadarData.addIndicatorData("售卖分", ssc.getMaxSsScore() + ssc.getMaxTurnOverScore());
        chartRadarData.addIndicatorData("风险分", ssc.getMaxRiskScore());

        ChartRadarSerieData serieData = new ChartRadarSerieData();
        serieData.setName("得分");
        serieData.getData().add((long) uss.getPriceScore());
        serieData.getData().add((long) uss.getAssetScore());
        serieData.getData().add((long) uss.getFgScore());
        serieData.getData().add((long) uss.getSsScore());
        serieData.getData().add((long) uss.getRiskScore());
        chartRadarData.getSeries().add(serieData);
        Map res = new HashMap();
        res.put("scoreInfo", uss);
        res.put("scoreStatData", chartRadarData);
        return callback(res);
    }

    /**
     * 获取评分的配置
     *
     * @return
     */
    @RequestMapping(value = "/getScoreConfig", method = RequestMethod.GET)
    public ResultBean getScoreConfig(UserCommonRequest ucr) {
        Long userId = ucr.getUserId();
        UserSharesScoreConfig ussc = sharesService.getUserSharesScoreConfig(userId);
        if (ussc == null) {
            ussc = new UserSharesScoreConfig();
            ussc.setUserId(userId);
            ussc.setCreatedTime(new Date());
            baseService.saveObject(ussc);
        }
        return callback(ussc);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/editScoreConfig", method = RequestMethod.POST)
    public ResultBean editScoreConfig(@RequestBody @Valid UserSharesScoreConfigFormRequest bean) {
        UserSharesScoreConfig ussc = sharesService.getUserSharesScoreConfig(bean.getUserId());
        if (ussc == null) {
            ussc = new UserSharesScoreConfig();
            ussc.setCreatedTime(new Date());
            ussc.setUserId(bean.getUserId());
            baseService.saveObject(ussc);
        }
        BeanCopy.copyProperties(bean, ussc);
        ussc.setLastModifyTime(new Date());
        baseService.updateObject(ussc);
        //重新设置缓存
        sharesHandler.resetScoreConfigCache(ussc);
        return callback(null);
    }


    /**
     * 警告记录按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/warnDateStat")
    public ResultBean warnDateStat(UserSharesWarnDateStatSearch sf) {
        if (sf.getDataGroupType() == UserSharesWarnDateStatSearch.DataGroupType.COUNT) {
            //按次数
            if (sf.getDateGroupType() == DateGroupType.DAYCALENDAR) {
                return callback(createChartCalendarData(sf));
            }
            List<UserSharesWarnDateStat> list = sharesService.statDateUserSharesWarn(sf);
            ChartData chartData = new ChartData();
            chartData.setTitle("股票警告统计");
            chartData.setLegendData(new String[]{"股票达到止损价格", "股票达到抛售价格", "股票连续下跌",
                    "股票连续上涨", "股票评分过低", "股票评分过高", "股票换手率过低", "股票换手率过高"});
            //可以根据涨跌类型分不同的组显示
            String fStack = "stack";
            String gStack = "stack";
            ChartYData y0Data = new ChartYData("股票达到止损价格", fStack);
            ChartYData y1Data = new ChartYData("股票达到抛售价格", gStack);
            ChartYData y2Data = new ChartYData("股票连续下跌", fStack);
            ChartYData y3Data = new ChartYData("股票连续上涨", gStack);
            ChartYData y4Data = new ChartYData("股票评分过低", fStack);
            ChartYData y5Data = new ChartYData("股票评分过高", gStack);
            ChartYData y6Data = new ChartYData("股票换手率过低", fStack);
            ChartYData y7Data = new ChartYData("股票换手率过高", gStack);

            //总的值
            BigDecimal totalCount = new BigDecimal(0);
            UserSharesWarnVo uswn = new UserSharesWarnVo();
            for (UserSharesWarnDateStat bean : list) {
                chartData.getIntXData().add(bean.getIndexValue());
                String indexName;
                if (sf.getDateGroupType() == DateGroupType.MONTH) {
                    indexName = bean.getIndexValue() + "月份";
                } else if (sf.getDateGroupType() == DateGroupType.YEAR) {
                    indexName = bean.getIndexValue() + "年";
                } else if (sf.getDateGroupType() == DateGroupType.WEEK) {
                    indexName = "第" + bean.getIndexValue() + "周";
                } else {
                    indexName = bean.getIndexValue().toString();
                }
                uswn.addData(indexName, bean.getWarnType().intValue(), bean.getTotalCount().intValue());
                totalCount = totalCount.add(new BigDecimal(bean.getTotalCount()));
            }
            for (UserSharesWarnDetailVo uswd : uswn.getList()) {
                chartData.getXdata().add(uswd.getIndexValue());
                y0Data.getData().add(uswd.getValues()[0]);
                y1Data.getData().add(uswd.getValues()[1]);
                y2Data.getData().add(uswd.getValues()[2]);
                y3Data.getData().add(uswd.getValues()[3]);
                y4Data.getData().add(uswd.getValues()[4]);
                y5Data.getData().add(uswd.getValues()[5]);
                y6Data.getData().add(uswd.getValues()[6]);
                y7Data.getData().add(uswd.getValues()[7]);
            }
            chartData.getYdata().add(y0Data);
            chartData.getYdata().add(y1Data);
            chartData.getYdata().add(y2Data);
            chartData.getYdata().add(y3Data);
            chartData.getYdata().add(y4Data);
            chartData.getYdata().add(y5Data);
            chartData.getYdata().add(y6Data);
            chartData.getYdata().add(y7Data);

            String totalString = totalCount.longValue() + "(次)";
            chartData.setSubTitle(this.getDateTitle(sf, totalString));
            chartData = ChartUtil.completeDate(chartData, sf);
            return callback(chartData);
        } else {
            return callback(createSharesDataStatPieData(sf));
        }

    }

    private ChartCalendarData createChartCalendarData(UserSharesWarnDateStatSearch sf) {
        List<UserSharesWarnDateStat> list = sharesService.statDateUserSharesWarn(sf);
        ChartCalendarData calendarData = ChartUtil.createChartCalendarData("股票警告统计", "次数", "次", sf, list);
        calendarData.setTop(3);
        return calendarData;
    }

    private ChartPieData createSharesDataStatPieData(UserSharesWarnDateStatSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("股票警告统计");
        ChartPieSerieData serieData = new ChartPieSerieData();
        UserSharesWarnDateStatSearch.DataGroupType dataGroupType = sf.getDataGroupType();
        if (dataGroupType == UserSharesWarnDateStatSearch.DataGroupType.WARN_TYPE) {
            serieData.setName("警告类型");
        } else {
            serieData.setName("股票名称");
        }
        List<UserSharesWarnDataStat> list = sharesService.statDataUserSharesWarn(sf);
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (UserSharesWarnDataStat bean : list) {
            String name = null;
            if (dataGroupType == UserSharesWarnDateStatSearch.DataGroupType.WARN_TYPE) {
                SharesWarnType warnType = SharesWarnType.getSharesWarnType(Integer.valueOf(bean.getGroupId().toString()));
                if (warnType == null) {
                    name = "未知";
                } else {
                    name = warnType.getName();
                }
            } else {
                UserShares us = baseService.getObject(UserShares.class, Long.valueOf(bean.getGroupId().toString()));
                name = us.getName();
            }
            chartPieData.getXdata().add(name);
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(name);
            dataDetail.setValue(bean.getTotalCount().intValue());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(new BigDecimal(bean.getTotalCount()));
        }
        String subTitle = this.getDateTitle(sf, String.valueOf(totalValue.intValue()) + "次");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }
}
