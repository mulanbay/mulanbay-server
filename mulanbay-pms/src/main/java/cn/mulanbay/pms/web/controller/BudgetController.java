package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.handler.bean.BudgetAmountBean;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.BudgetLog;
import cn.mulanbay.pms.persistent.domain.BudgetTimeline;
import cn.mulanbay.pms.persistent.dto.BudgetStat;
import cn.mulanbay.pms.persistent.dto.BuyRecordBudgetStat;
import cn.mulanbay.pms.persistent.dto.BuyRecordRealTimeStat;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.GoodsConsumeType;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.service.BudgetService;
import cn.mulanbay.pms.persistent.service.BuyRecordService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.buy.BuyRecordAnalyseStatSearch;
import cn.mulanbay.pms.web.bean.request.fund.*;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.fund.BudgetAnalyseVo;
import cn.mulanbay.pms.web.bean.response.fund.BudgetDetailVo;
import cn.mulanbay.pms.web.bean.response.fund.BudgetInfoVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ??????
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/budget")
public class BudgetController extends BaseController {

    private static Class<Budget> beanClass = Budget.class;

    @Autowired
    BudgetService budgetService;

    @Autowired
    BudgetHandler budgetHandler;

    @Autowired
    BuyRecordService buyRecordService;

    /**
     * ?????????
     * @param needRoot
     * @param filterEmpty ????????????
     * @return
     */
    @RequestMapping(value = "/getBudgetTree")
    public ResultBean getBudgetTree(Boolean needRoot,Boolean filterEmpty) {
        try {
            BudgetSearch sf = new BudgetSearch();
            PageRequest pr = sf.buildQuery();
            pr.setPage(PageRequest.NO_PAGE);
            pr.setBeanClass(beanClass);
            Sort s1 = new Sort("period", Sort.ASC);
            pr.addSort(s1);
            List<Budget> gtList = baseService.getBeanList(pr);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (PeriodType period : PeriodType.values()) {
                TreeBean tb = new TreeBean();
                tb.setId("p" + period.getValue());
                tb.setText(period.getName());
                for (Budget nc : gtList) {
                    if (nc.getPeriod() == period) {
                        TreeBean child = new TreeBean();
                        child.setId(nc.getId().toString());
                        child.setText(nc.getName());
                        tb.addChild(child);
                    }
                }
                //???????????????
                if((filterEmpty==null||!filterEmpty)|| (filterEmpty&&tb.hasChildren())){
                    list.add(tb);
                }
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "???????????????????????????",
                    e);
        }
    }

    /**
     * ????????????
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BudgetSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s1 = new Sort("status", Sort.DESC);
        pr.addSort(s1);
        Sort s2 = new Sort("createdTime", Sort.DESC);
        pr.addSort(s2);
        PageResult<Budget> qr = baseService.getBeanResult(pr);
        PageResult<BudgetDetailVo> res = new PageResult<>(qr);
        List<BudgetDetailVo> list = new ArrayList<>();
        Date now = new Date();
        for (Budget bg : qr.getBeanList()) {
            BudgetDetailVo bdb = this.getDetail(bg,now);
            list.add(bdb);
        }
        res.setBeanList(list);
        return callbackDataGrid(res);
    }

    /**
     * ????????????
     * @param bg
     * @param now
     * @return
     */
    private BudgetDetailVo getDetail(Budget bg,Date now){
        BudgetDetailVo bdb = new BudgetDetailVo();
        BeanCopy.copyProperties(bg, bdb);
        if (bg.getStatus() == CommonStatus.ENABLE) {
            //????????????????????????????????????
            if (bg.getFeeType()!=null) {
                BuyRecordBudgetStat bs= budgetHandler.getActualAmount(bg,now);
                if (bs.getTotalPrice() != null) {
                    bdb.setCpPaidTime(bs.getMaxBuyDate());
                    bdb.setCpPaidAmount(bs.getTotalPrice().doubleValue());
                }else{
                    //???????????????????????????
                    Date nextPayTime = budgetHandler.getNextPayTime(bg, now);
                    bdb.setNextPaytime(nextPayTime);
                    Integer ld = DateUtil.getIntervalDays(now, nextPayTime);
                    bdb.setLeftDays(ld);
                }
            }
        }
        return bdb;
    }
    /**
     * ??????
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid BudgetFormRequest bean) {
        Budget budget = new Budget();
        BeanCopy.copyProperties(bean, budget);
        budget.setCreatedTime(new Date());
        baseService.saveObject(budget);
        return callback(null);
    }


    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest gr) {
        Budget budget = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        return callback(budget);
    }

    /**
     * ??????
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid BudgetFormRequest bean) {
        //?????????????????????????????????????????????????????????
        if(bean.getPeriod()==PeriodType.MONTHLY&&bean.getStatus()==CommonStatus.DISABLE){
            int year = DateUtil.getYear(new Date());
            long n = budgetService.countMonthBudgetSnapshot(bean.getId(),year);
            if(n>0){
                return this.callbackErrorInfo("??????????????????????????????"+n+"????????????????????????????????????????????????????????????????????????????????????0?????????????????????????????????");
            }
        }
        Budget budget = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        BeanCopy.copyProperties(bean, budget);
        budget.setLastModifyTime(new Date());
        baseService.updateObject(budget);
        return callback(null);
    }

    /**
     * ??????
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        this.deleteUserEntity(beanClass,deleteRequest.getIds(),Long.class,deleteRequest.getUserId());
        return callback(null);
    }

    /**
     *
     * @param sf
     * @return
     */
    @RequestMapping(value = "/getInfoList", method = RequestMethod.GET)
    public ResultBean getInfoList(@Valid BudgetInfoListSearch sf) {
        String bussKey = budgetHandler.createBussKey(sf.getPeriod(), new Date());
        List<Budget> list = budgetService.getActiveUserBudget(sf.getUserId(), null);
        BudgetAmountBean bab = budgetHandler.calcBudgetAmount(list, new Date());
        List<Budget> newList = null;
        if (sf.getPeriod() == PeriodType.MONTHLY) {
            newList = bab.getMonthBudgetList();
        } else if (sf.getPeriod() == PeriodType.YEARLY) {
            newList = bab.getYearBudgetList();
        } else {
            return callbackErrorInfo("??????????????????????????????:" + sf.getPeriod());
        }
        List<BudgetDetailVo> res = new ArrayList<>();
        Date now = new Date();
        for (Budget bg : newList) {
            BudgetDetailVo bdb = this.getDetail(bg,now);
            res.add(bdb);
        }
        return callback(res);
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping(value = "/analyse", method = RequestMethod.GET)
    public ResultBean analyse(@Valid BudgetAnalyseSearch sf) {
        Date[] ds = budgetHandler.getDateRange(sf.getPeriod(), sf.getDate(), true);
        BudgetAnalyseVo res = new BudgetAnalyseVo();
        if (sf.getPeriod() == PeriodType.MONTHLY) {
            res.setTitle(DateUtil.getFormatDate(sf.getDate(), "yyyy-MM") + "??????????????????");
        } else {
            res.setTitle(DateUtil.getFormatDate(sf.getDate(), "yyyy") + "??????????????????");
        }
        List<Budget> list = budgetService.getActiveUserBudget(sf.getUserId(), null);
        Date now = new Date();
        double amount = 0;
        for (Budget b : list) {
            if (b.getPeriod() == PeriodType.ONCE) {
                if (b.getExpectPaidTime() == null) {
                    //?????????????????????????????????????????????????????????
                    continue;
                } else if (b.getExpectPaidTime().after(ds[0]) && b.getExpectPaidTime().before(ds[1])) {
                    //??????
                    BudgetInfoVo bib = new BudgetInfoVo();
                    BeanCopy.copyProperties(b, bib);
                    Integer n = budgetHandler.getLeftDays(b, now);
                    bib.setLeftDays(n);
                    res.addBudget(bib);
                    amount += b.getAmount();
                } else {
                    continue;
                }
            } else {
                //??????
                BudgetInfoVo bib = new BudgetInfoVo();
                BeanCopy.copyProperties(b, bib);
                Integer n = budgetHandler.getLeftDays(b, now);
                bib.setLeftDays(n);
                if (b.getPeriod() == PeriodType.DAILY) {
                    if (sf.getPeriod() == PeriodType.MONTHLY) {
                        bib.setDrate(30);
                    } else {
                        bib.setDrate(365);
                    }
                } else if (b.getPeriod() == PeriodType.WEEKLY) {
                    if (sf.getPeriod() == PeriodType.MONTHLY) {
                        bib.setDrate(4);
                    } else {
                        bib.setDrate(52);
                    }
                } else if (b.getPeriod() == PeriodType.MONTHLY) {
                    if (sf.getPeriod() == PeriodType.MONTHLY) {
                        bib.setDrate(1);
                    } else {
                        bib.setDrate(12);
                    }
                } else if (b.getPeriod() == PeriodType.QUARTERLY) {
                    if (sf.getPeriod() == PeriodType.MONTHLY) {
                        //???????????????
                        continue;
                    } else {
                        bib.setDrate(4);
                    }
                } else if (b.getPeriod() == PeriodType.YEARLY) {
                    if (sf.getPeriod() == PeriodType.MONTHLY) {
                        //????????????????????????????????????
                        if (b.getExpectPaidTime() == null) {
                            continue;
                        } else if (b.getExpectPaidTime().after(ds[0]) && b.getExpectPaidTime().before(ds[1])) {

                        } else {
                            continue;
                        }
                    }
                }
                res.addBudget(bib);
                amount += b.getAmount() * bib.getDrate();
            }
        }
        //????????????
        res.setBudgetAmount(amount);
        //?????????????????????
        double treatAmount = budgetHandler.getTreadConsume(ds[0], ds[1], sf.getUserId());
        res.setTrAmount(treatAmount);
        double ncAmount = buyRecordService.statBuyAmount(ds[0], ds[1], sf.getUserId(), (short) GoodsConsumeType.NORMAL.getValue());
        res.setNcAmount(ncAmount);
        double bcAmount = buyRecordService.statBuyAmount(ds[0], ds[1], sf.getUserId(), (short) GoodsConsumeType.OUTBURST.getValue());
        res.setBcAmount(bcAmount);
        //todo ????????????

        return callback(res);
    }

    /**
     * ??????
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(BudgetStatSearch as) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("?????????????????????");
        chartPieData.setUnit("???");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("??????(?????????)");
        //?????????
        BigDecimal totalValue = new BigDecimal(0);
        List<BudgetStat> list = budgetService.statBudget(as.getUserId(), as.getType(), as.getPeriod(), as.getStatType());
        for (BudgetStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getValue());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(bean.getValue());
        }
        String subTitle = "??????????????????" + String.valueOf(totalValue.longValue()) + "???";
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * ??????
     *
     * @return
     */
    @RequestMapping(value = "/statByKeywords", method = RequestMethod.GET)
    public ResultBean statByKeywords(BudgetStatByKeywordsSearch as) {
        BuyRecordAnalyseStatSearch basf = new BuyRecordAnalyseStatSearch();
        basf.setUserId(as.getUserId());
        basf.setGroupField(as.getGroupField());
        basf.setStartDate(as.getStartDate());
        basf.setEndDate(as.getEndDate());
        Budget budget = this.getUserEntity(beanClass, as.getId(), as.getUserId());
        basf.setKeywords(budget.getKeywords());
        basf.setType(GroupType.TOTALPRICE);
        List<BuyRecordRealTimeStat> list = buyRecordService.getAnalyseStat(basf);

        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("[" + budget.getName() + "]?????????????????????");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("??????");
        //?????????
        double totalValue = 0;
        for (BuyRecordRealTimeStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getValue());
            serieData.getData().add(dataDetail);
            totalValue += bean.getValue();
        }
        String subTitle = "???????????????" + PriceUtil.changeToString(2, budget.getAmount()) + "???,????????????:" + PriceUtil.changeToString(2, totalValue) + "???";
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * ???????????????
     *
     * @return
     */
    @RequestMapping(value = "/timelineStat")
    public ResultBean timelineStat(@Valid BudgetTimelineStatSearch sf) {
        String bussKey = budgetHandler.createBussKey(sf.getPeriod(), sf.getBussDay());
        List<BudgetTimeline> list = budgetService.selectBudgetTimelineList(bussKey, sf.getUserId());
        String dateFormat = "yyyy-MM";
        if (PeriodType.YEARLY == sf.getPeriod()) {
            dateFormat = "yyyy";
        }
        ChartData chartData = new ChartData();
        chartData.setTitle("[" + DateUtil.getFormatDate(sf.getBussDay(), dateFormat) + "]?????????????????????");
        String[] ld;
        if (sf.getStatType() == BudgetTimelineStatSearch.StatType.RATE) {
            ld = new String[]{"??????/????????????", "????????????"};
            chartData.setUnit("%");
        } else {
            ld = new String[]{"????????????", "????????????"};
            chartData.setUnit("???");
        }
        chartData.setLegendData(ld);
        ChartYData cbData = new ChartYData();
        cbData.setName(ld[0]);
        ChartYData timeData = new ChartYData();
        timeData.setName(ld[1]);
        if (list.isEmpty()) {
            return callback(chartData);
        }
        for (BudgetTimeline tl : list) {
            if (sf.getPeriod() == PeriodType.MONTHLY) {
                chartData.getXdata().add(tl.getPassDays() + "???");
            } else {
                chartData.getXdata().add(DateUtil.getFormatDate(tl.getBussDay(), "MM-dd"));
            }
            chartData.getIntXData().add(tl.getPassDays());
            double consumeAmount;
            if (sf.getConsumeType() == null) {
                consumeAmount = tl.getNcAmount() + tl.getBcAmount() + tl.getTrAmount();
            } else if (sf.getConsumeType() == GoodsConsumeType.NORMAL) {
                consumeAmount = tl.getNcAmount() + tl.getTrAmount();
            } else {
                consumeAmount = tl.getBcAmount() + tl.getTrAmount();
            }
            if (sf.getStatType() == BudgetTimelineStatSearch.StatType.RATE) {
                double cbRate = NumberUtil.getPercentValue(consumeAmount, tl.getBudgetAmount(), 0);
                double dayRate = NumberUtil.getPercentValue(tl.getPassDays(), tl.getTotalDays(), 0);
                cbData.getData().add(cbRate);
                timeData.getData().add(dayRate);
            } else {
                cbData.getData().add(PriceUtil.changeToString(2, consumeAmount));
                timeData.getData().add(PriceUtil.changeToString(2, tl.getBudgetAmount()));
            }
        }
        BudgetTimeline end = list.get(list.size() - 1);
        double tt = end.getNcAmount() + end.getBcAmount() + end.getTrAmount();
        String subTitle = "????????????????????????" + PriceUtil.changeToString(2, tt) + "???????????????" + PriceUtil.changeToString(2, end.getBudgetAmount()) + "??????";
        if (tt > end.getBudgetAmount()) {
            subTitle += "(??????????????????)";
        }
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(cbData);
        chartData.getYdata().add(timeData);
        return callback(chartData);
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    @RequestMapping(value = "/reStatTimeline", method = RequestMethod.POST)
    public ResultBean reStatTimeline(@RequestBody @Valid BudgetReStatTimelineRequest re) {
        Date firstDay;
        Date lastDay;
        if (re.getPeriod() == PeriodType.MONTHLY) {
            firstDay = DateUtil.getFirstDayOfMonth(re.getBussDay());
            lastDay = DateUtil.getLastDayOfMonth(re.getBussDay());
        } else {
            firstDay = DateUtil.getYearFirst(re.getBussDay());
            lastDay = DateUtil.getLastDayOfYear(re.getBussDay());
        }
        //???????????????????????????
        Date max = DateUtil.getDate(-1, new Date());
        if (lastDay.after(max)) {
            lastDay = max;
        }
        List<BudgetTimeline> datas = new ArrayList<>();
        String bussKey = budgetHandler.createBussKey(re.getPeriod(), re.getBussDay());
        Date cc = firstDay;
        //?????????
        int tds;
        List<Budget> list = budgetService.getActiveUserBudget(re.getUserId(), null);
        BudgetAmountBean bab = budgetHandler.calcBudgetAmount(list, firstDay);
        double budgetAmount;
        if (re.getPeriod() == PeriodType.MONTHLY) {
            //???????????????
            tds = DateUtil.getMonthDays(firstDay);
            budgetAmount = bab.getMonthBudget();
        } else {
            //???????????????
            tds = DateUtil.getDays(Integer.valueOf(DateUtil.getYear(firstDay)));
            budgetAmount = bab.getYearBudget();
        }

        while (!cc.after(lastDay)) {
            //??????????????????
            int pds;
            if (re.getPeriod() == PeriodType.MONTHLY) {
                //????????????????????????
                pds = DateUtil.getDayOfMonth(cc);
            } else {
                //????????????????????????
                pds = DateUtil.getDayOfYear(cc);
            }
            //?????????????????????
            BudgetTimeline timeline = new BudgetTimeline();
            BudgetLog bl = budgetHandler.statBudget(re.getUserId(), budgetAmount, firstDay, cc, bussKey, false, re.getPeriod());
            BeanCopy.copyProperties(bl, timeline);
            timeline.setCreatedTime(new Date());
            timeline.setLastModifyTime(null);
            timeline.setBussDay(cc);
            timeline.setTotalDays(tds);
            timeline.setPassDays(pds);
            timeline.setBussKey(bussKey);
            timeline.setId(null);
            timeline.setRemark("??????????????????");
            datas.add(timeline);
            cc = DateUtil.getDate(1, cc);
        }
        budgetService.reSaveBudgetTimeline(datas, bussKey, re.getUserId());
        return callback(null);
    }
}
