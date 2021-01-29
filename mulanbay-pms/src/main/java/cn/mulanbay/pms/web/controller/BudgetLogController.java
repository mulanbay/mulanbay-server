package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.*;
import cn.mulanbay.persistent.query.NullType;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.BudgetHandler;
import cn.mulanbay.pms.persistent.domain.Budget;
import cn.mulanbay.pms.persistent.domain.BudgetLog;
import cn.mulanbay.pms.persistent.dto.UserBudgetAndIncomeStat;
import cn.mulanbay.pms.persistent.enums.BudgetLogSource;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.service.BudgetService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.fund.*;
import cn.mulanbay.pms.web.bean.response.chart.ChartData;
import cn.mulanbay.pms.web.bean.response.chart.ChartYData;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 预算日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/budgetLog")
public class BudgetLogController extends BaseController {

    private static Class<BudgetLog> beanClass = BudgetLog.class;

    @Autowired
    BudgetService budgetService;

    @Autowired
    BudgetHandler budgetHandler;

    /**
     * 获取列表
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(BudgetLogSearch sf) {
        String budgetKey = sf.getBudgetKey();
        if (StringUtil.isNotEmpty(budgetKey)) {
            if (budgetKey.startsWith("p")) {
                //说明是分组过来的，即period的大节点
                int period = Integer.valueOf(sf.getBudgetKey().replace("p", ""));
                PeriodType bp = PeriodType.getPeriodType(period);
                sf.setPeriod(bp);
            } else {
                Long budgetId = Long.valueOf(sf.getBudgetKey());
                sf.setBudgetId(budgetId);
            }
        }
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s1 = new Sort("createdTime", Sort.DESC);
        pr.addSort(s1);
        PageResult<BudgetLog> qr = baseService.getBeanResult(pr);
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid BudgetLogFormRequest bean) {
        BudgetLog budgetLog = new BudgetLog();
        BeanCopy.copyProperties(bean, budgetLog, true);
        Budget budget = this.getUserEntity(Budget.class, bean.getBudgetId(), bean.getUserId());
        budgetLog.setBudget(budget);
        //计算业务key
        String bussKey = budgetHandler.createBussKey(budget.getPeriod(), budgetLog.getOccurDate());
        boolean isBussKeyExit = budgetService.isBudgetLogExit(bussKey, bean.getUserId(), bean.getId(), budget.getId());
        if (isBussKeyExit) {
            return callbackErrorCode(PmsErrorCode.BUDGET_LOG_EXIT);
        }
        budgetLog.setPeriod(budget.getPeriod());
        budgetLog.setBussKey(bussKey);
        budgetLog.setBudgetAmount(budget.getAmount());
        budgetLog.setNcAmount(bean.getAmount());
        budgetLog.setBcAmount(0.0);
        budgetLog.setTrAmount(0.0);
        budgetLog.setIncomeAmount(0.0);
        budgetLog.setSource(BudgetLogSource.MANUAL);
        budgetLog.setCreatedTime(new Date());
        budgetService.saveBudgetLog(budgetLog, false);
        return callback(null);
    }

    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest gr) {
        BudgetLog budgetLog = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        return callback(budgetLog);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid BudgetLogFormRequest bean) {
        BudgetLog budgetLog = this.getUserEntity(beanClass, bean.getId(), bean.getUserId());
        BeanCopy.copyProperties(bean, budgetLog, true);
        Budget budget = this.getUserEntity(Budget.class, bean.getBudgetId(), bean.getUserId());
        budgetLog.setBudget(budget);
        //计算业务key
        String bussKey = budgetHandler.createBussKey(budget.getPeriod(), budgetLog.getOccurDate());
        boolean isBussKeyExit = budgetService.isBudgetLogExit(bussKey, bean.getUserId(), bean.getId(), budget.getId());
        if (isBussKeyExit) {
            return callbackErrorCode(PmsErrorCode.BUDGET_LOG_EXIT);
        }
        budgetLog.setPeriod(budget.getPeriod());
        budgetLog.setBudgetAmount(budget.getAmount());
        budgetLog.setNcAmount(bean.getAmount());
        budgetLog.setBcAmount(0.0);
        budgetLog.setTrAmount(0.0);
        budgetLog.setIncomeAmount(0.0);
        budgetLog.setBussKey(bussKey);
        budgetLog.setLastModifyTime(new Date());
        baseService.updateObject(budgetLog);
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
     * 统计(这里的收入是实时获取的，以前的接口中收入还没有统计到BudgetLog中)
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(@Valid BudgetLogStatSearch sf) {
        if (sf.getBudgetKey().startsWith("p")) {
            //说明是分组过来的，即period的大节点
            int period = Integer.valueOf(sf.getBudgetKey().replace("p", ""));
            PeriodType bp = PeriodType.getPeriodType(period);
            List<UserBudgetAndIncomeStat> list = budgetService.statUserBudgetAndIncome(sf.getStartDate(), sf.getEndDate(),
                    sf.getUserId(), bp);
            ChartData chartData = new ChartData();
            chartData.setTitle(bp.getName() + "预算统计");
            chartData.setLegendData(new String[]{"预算(元)", "实际花费(元)", "收入(元)","花费/预算比率(%)"});
            ChartYData budgetData = new ChartYData("预算(元)");
            ChartYData consumeData = new ChartYData("实际花费(元)");
            ChartYData incomeData = new ChartYData("收入(元)");
            ChartYData rateData = new ChartYData("花费/预算比率(%)");
            //混合图形下使用
            chartData.addYAxis("金额","元");
            chartData.addYAxis("比率","%");
            for (UserBudgetAndIncomeStat bean : list) {
                String xformat = DateUtil.FormatDay1;
                if (bp == PeriodType.YEARLY) {
                    xformat = "yyyy";
                } else if (bp == PeriodType.MONTHLY) {
                    xformat = "yyyy-MM";
                }
                chartData.getXdata().add(DateUtil.getFormatDate(bean.getOccurDate(), xformat));
                budgetData.getData().add(bean.getBudgetAmount());
                BigDecimal consume = bean.getNcAmount().add(bean.getTrAmount());
                if (sf.getNeedOutBurst() != null && sf.getNeedOutBurst() == true) {
                    consume = consume.add(bean.getBcAmount());
                }
                consumeData.getData().add(PriceUtil.changeToString(0, consume));
                double rate = NumberUtil.getPercentValue(consume.doubleValue(),bean.getBudgetAmount().doubleValue(),0);
                rateData.getData().add(rate);
                incomeData.getData().add(bean.getTotalIncome() == null ? 0 : bean.getTotalIncome());
            }
            chartData.getYdata().add(budgetData);
            chartData.getYdata().add(consumeData);
            chartData.getYdata().add(incomeData);
            chartData.getYdata().add(rateData);

            //String subTitle = this.getDateTitle(sf, totalCount.longValue()+"次");
            //chartData.setSubTitle(subTitle);
            return callback(chartData);
        } else {
            Long budgetId = Long.valueOf(sf.getBudgetKey());
            sf.setBudgetId(budgetId);
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            Sort s1 = new Sort("occurDate", Sort.ASC);
            pr.addSort(s1);
            PageResult<BudgetLog> qr = baseService.getBeanResult(pr);
            ChartData chartData = new ChartData();
            Budget budget = this.getUserEntity(Budget.class, budgetId, sf.getUserId());

            chartData.setTitle(budget.getName() + "预算统计");
            chartData.setLegendData(new String[]{"预算", "实际花费"});
            ChartYData budgetData = new ChartYData("预算");
            ChartYData consumeData = new ChartYData("实际花费");

            for (BudgetLog bean : qr.getBeanList()) {
                chartData.getXdata().add(DateUtil.getFormatDate(bean.getOccurDate(), DateUtil.FormatDay1));
                budgetData.getData().add(bean.getBudgetAmount());
                //单个的消费只保存在普通消费字段里面
                consumeData.getData().add(PriceUtil.changeToString(2, bean.getNcAmount()));
            }
            chartData.getYdata().add(budgetData);
            chartData.getYdata().add(consumeData);

            //String subTitle = this.getDateTitle(sf, totalCount.longValue()+"次");
            //chartData.setSubTitle(subTitle);
            return callback(chartData);
        }
    }

    /**
     * 实际的账户变化与系统计算的存款变化
     *
     * @return
     */
    @RequestMapping(value = "/valueErrorStat", method = RequestMethod.GET)
    public ResultBean valueErrorStat(@Valid BudgetLogValueErrorStatSearch sf) {
        sf.setAccountChangeAmount(NullType.NOT_NULL);
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s1 = new Sort("bussKey", Sort.ASC);
        pr.addSort(s1);
        List<BudgetLog> list = baseService.getBeanList(pr);
        ChartData chartData = new ChartData();
        chartData.setTitle("账户变化与系统计算误差统计");
        chartData.setLegendData(new String[]{"误差值(元)","误差率(%)"});
        //混合图形下使用
        chartData.addYAxis("金额","元");
        chartData.addYAxis("比率","%");
        ChartYData yData1 = new ChartYData();
        yData1.setName("误差值(元)");
        ChartYData yData2 = new ChartYData();
        yData2.setName("误差率(%)");
        for (BudgetLog bean : list) {
            chartData.getIntXData().add(Integer.valueOf(bean.getBussKey()));
            chartData.getXdata().add(bean.getBussKey());
            //存款变化
            double mv = bean.getIncomeAmount()-(bean.getBcAmount()+bean.getNcAmount()+bean.getTrAmount());
            //账户变化
            double ev = bean.getAccountChangeAmount();
            double e = ev-mv;
            yData1.getData().add(PriceUtil.changeToString(2,e));
            double pp = NumberUtil.getPercentValue(e,Math.abs(mv),0);
            yData2.getData().add(pp);
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        return callback(chartData);
    }

    /**
     * 统计（这里的收入是日终统计到BudgetLog中了，后期新增了字段）
     *
     * @return
     */
    @RequestMapping(value = "/getPeriodStat", method = RequestMethod.GET)
    public ResultBean getPeriodStat(@Valid BudgetLogPeriodStatSearch sf) {
        BudgetLog bl = budgetService.selectBudgetLog(sf.getBussKey(), sf.getUserId());
        return callback(bl);
    }

    /**
     * 重新保存
     *
     * @return
     */
    @RequestMapping(value = "/reSave", method = RequestMethod.POST)
    public ResultBean reSave(@RequestBody @Valid CommonBeanGetRequest gr) {
        BudgetLog budgetLog = this.getUserEntity(beanClass, gr.getId(), gr.getUserId());
        Date bussDay = budgetLog.getOccurDate();
        List<Budget> list = budgetService.getActiveUserBudget(gr.getUserId(), null);
        //默认是每月、每年的统计的日志
        boolean useLastDay = true;
        if (budgetLog.getBudget() != null) {
            //重新统计统计的是当时那一天为主的值
            useLastDay = false;
        }
        budgetHandler.statAndSaveBudgetLog(list, gr.getUserId(), bussDay, budgetLog.getBussKey(), true, budgetLog.getPeriod(), useLastDay);
        return callback(null);
    }


}
