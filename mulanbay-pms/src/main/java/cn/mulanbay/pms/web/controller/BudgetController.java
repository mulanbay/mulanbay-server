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
 * 预算
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
     * 预算树
     * @param needRoot
     * @param filterEmpty 过滤空树
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
                //去掉空列表
                if((filterEmpty==null||!filterEmpty)|| (filterEmpty&&tb.hasChildren())){
                    list.add(tb);
                }
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取预算列表树异常",
                    e);
        }
    }

    /**
     * 获取列表
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
            BudgetDetailVo bdb = new BudgetDetailVo();
            BeanCopy.copyProperties(bg, bdb);
            if (bg.getStatus() == CommonStatus.ENABLE) {
                //计算下一次支付时间
                Date nextPayTime = budgetHandler.getNextPayTime(bg, now);
                if (nextPayTime != null) {
                    bdb.setNextPaytime(nextPayTime);
                    Integer ld = DateUtil.getIntervalDays(now, nextPayTime);
                    bdb.setLeftDays(ld);
                }
                //直接根据实际花费实时查询
                if (bg.getFeeType()!=null) {
                    BuyRecordBudgetStat bs= budgetHandler.getActualAmount(bg,now);
                    if (bs.getTotalPrice() != null) {
                        bdb.setCpPaidTime(bs.getMaxBuyDate());
                        bdb.setCpPaidAmount(bs.getTotalPrice().doubleValue());
                    }
                }
            }
            list.add(bdb);
        }
        res.setBeanList(list);
        return callbackDataGrid(res);
    }

    /**
     * 创建
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
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest gr) {
        Budget budget = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        return callback(budget);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid BudgetFormRequest bean) {
        Budget budget = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        BeanCopy.copyProperties(bean, budget);
        budget.setLastModifyTime(new Date());
        baseService.updateObject(budget);
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
            return callbackErrorInfo("不支持的周期查询类型:" + sf.getPeriod());
        }
        List<BudgetInfoVo> res = new ArrayList<>();
        Date now = new Date();
        for (Budget bg : newList) {
            BudgetInfoVo bib = new BudgetInfoVo();
            BeanCopy.copyProperties(bg, bib);
            BudgetLog bl = budgetService.selectBudgetLog(bussKey, sf.getUserId(), null, bg.getId());
            if (bl != null) {
                BeanCopy.copyProperties(bl, bib);
            }
            if (bg.getExpectPaidTime() != null) {
                Integer n = budgetHandler.getLeftDays(bg, now);
                bib.setLeftDays(n);
            }
            res.add(bib);
        }
        return callback(res);
    }

    /**
     * 预算分析
     *
     * @return
     */
    @RequestMapping(value = "/analyse", method = RequestMethod.GET)
    public ResultBean analyse(@Valid BudgetAnalyseSearch sf) {
        Date[] ds = budgetHandler.getDateRange(sf.getPeriod(), sf.getDate(), true);
        BudgetAnalyseVo res = new BudgetAnalyseVo();
        if (sf.getPeriod() == PeriodType.MONTHLY) {
            res.setTitle(DateUtil.getFormatDate(sf.getDate(), "yyyy-MM") + "月度预算分析");
        } else {
            res.setTitle(DateUtil.getFormatDate(sf.getDate(), "yyyy") + "年度预算分析");
        }
        List<Budget> list = budgetService.getActiveUserBudget(sf.getUserId(), null);
        Date now = new Date();
        double amount = 0;
        for (Budget b : list) {
            if (b.getPeriod() == PeriodType.ONCE) {
                if (b.getExpectPaidTime() == null) {
                    //如果是单次，且没有具体实现日期，则跳过
                    continue;
                } else if (b.getExpectPaidTime().after(ds[0]) && b.getExpectPaidTime().before(ds[1])) {
                    //加入
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
                //加入
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
                        //月度的跳过
                        continue;
                    } else {
                        bib.setDrate(4);
                    }
                } else if (b.getPeriod() == PeriodType.YEARLY) {
                    if (sf.getPeriod() == PeriodType.MONTHLY) {
                        //查询实际是否在这段时间内
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
        //设置预算
        res.setBudgetAmount(amount);
        //查询已经消费的
        double treatAmount = budgetHandler.getTreadConsume(ds[0], ds[1], sf.getUserId());
        res.setTrAmount(treatAmount);
        double ncAmount = buyRecordService.statBuyAmount(ds[0], ds[1], sf.getUserId(), (short) GoodsConsumeType.NORMAL.getValue());
        res.setNcAmount(ncAmount);
        double bcAmount = buyRecordService.statBuyAmount(ds[0], ds[1], sf.getUserId(), (short) GoodsConsumeType.OUTBURST.getValue());
        res.setBcAmount(bcAmount);
        //todo 查询收入

        return callback(res);
    }

    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(BudgetStatSearch as) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("年预算费用分析");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("预算(年费用)");
        //总的值
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
        String subTitle = "年预算总计：" + String.valueOf(totalValue.longValue()) + "元";
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * 统计
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
        chartPieData.setTitle("[" + budget.getName() + "]预算的消费分析");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("费用");
        //总的值
        double totalValue = 0;
        for (BuyRecordRealTimeStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(bean.getName());
            dataDetail.setValue(bean.getValue());
            serieData.getData().add(dataDetail);
            totalValue += bean.getValue();
        }
        String subTitle = "预算金额：" + PriceUtil.changeToString(2, budget.getAmount()) + "元,实际花费:" + PriceUtil.changeToString(2, totalValue) + "元";
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * 时间线统计
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
        chartData.setTitle("[" + DateUtil.getFormatDate(sf.getBussDay(), dateFormat) + "]预算与消费统计");
        String[] ld;
        if (sf.getStatType() == BudgetTimelineStatSearch.StatType.RATE) {
            ld = new String[]{"消费/预算比例(%)", "时间进度(%)"};
        } else {
            ld = new String[]{"消费金额(元)", "预算金额(元)"};
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
                chartData.getXdata().add(tl.getPassDays() + "号");
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
        String subTitle = "当前消费总金额：" + PriceUtil.changeToString(2, tt) + "元，预算：" + PriceUtil.changeToString(2, end.getBudgetAmount()) + "元。";
        if (tt > end.getBudgetAmount()) {
            subTitle += "(已经超出预算)";
        }
        chartData.setSubTitle(subTitle);
        chartData.getYdata().add(cbData);
        chartData.getYdata().add(timeData);
        return callback(chartData);
    }

    /**
     * 重新统计时间线
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
        //最多只能统计到昨天
        Date max = DateUtil.getDate(-1, new Date());
        if (lastDay.after(max)) {
            lastDay = max;
        }
        List<BudgetTimeline> datas = new ArrayList<>();
        String bussKey = budgetHandler.createBussKey(re.getPeriod(), re.getBussDay());
        Date cc = firstDay;
        //总天数
        int tds;
        List<Budget> list = budgetService.getActiveUserBudget(re.getUserId(), null);
        BudgetAmountBean bab = budgetHandler.calcBudgetAmount(list, firstDay);
        double budgetAmount;
        if (re.getPeriod() == PeriodType.MONTHLY) {
            //当月总天数
            tds = DateUtil.getMonthDays(firstDay);
            budgetAmount = bab.getMonthBudget();
        } else {
            //当年总天数
            tds = DateUtil.getDays(Integer.valueOf(DateUtil.getYear(firstDay)));
            budgetAmount = bab.getYearBudget();
        }

        while (!cc.after(lastDay)) {
            //已经过去天数
            int pds;
            if (re.getPeriod() == PeriodType.MONTHLY) {
                //当月已经过去几天
                pds = DateUtil.getDayOfMonth(cc);
            } else {
                //当年已经过去几天
                pds = DateUtil.getDayOfYear(cc);
            }
            //增加时间线流水
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
            timeline.setRemark("手动重新统计");
            datas.add(timeline);
            cc = DateUtil.getDate(1, cc);
        }
        budgetService.reSaveBudgetTimeline(datas, bussKey, re.getUserId());
        return callback(null);
    }
}
