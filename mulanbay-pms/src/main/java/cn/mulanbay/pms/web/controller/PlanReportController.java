package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.MLConstant;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.ReportHandler;
import cn.mulanbay.pms.handler.ThreadPoolHandler;
import cn.mulanbay.pms.handler.UserScoreHandler;
import cn.mulanbay.pms.persistent.domain.PlanReport;
import cn.mulanbay.pms.persistent.domain.PlanReportTimeline;
import cn.mulanbay.pms.persistent.domain.UserPlan;
import cn.mulanbay.pms.persistent.domain.UserPlanConfigValue;
import cn.mulanbay.pms.persistent.dto.PlanReportAvgStat;
import cn.mulanbay.pms.persistent.dto.PlanReportPlanCommendDto;
import cn.mulanbay.pms.persistent.dto.PlanReportResultGroupStat;
import cn.mulanbay.pms.persistent.dto.PlanReportSummaryStat;
import cn.mulanbay.pms.persistent.enums.*;
import cn.mulanbay.pms.persistent.service.PlanService;
import cn.mulanbay.pms.util.ChartUtil;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonTreeSearch;
import cn.mulanbay.pms.web.bean.request.plan.*;
import cn.mulanbay.pms.web.bean.request.report.PlanReportReStatTimelineRequest;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.pms.web.bean.response.plan.PlanReportPredictVo;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * 计划执行报告
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/planReport")
public class PlanReportController extends BaseController {

    private static Class<PlanReport> beanClass = PlanReport.class;

    @Autowired
    PlanService planService;

    @Autowired
    ThreadPoolHandler threadPoolHandler;

    @Autowired
    UserScoreHandler userScoreHandler;

    @Autowired
    ReportHandler reportHandler;

    /**
     * 计划报告树
     *
     * @return
     */
    @RequestMapping(value = "/getPlanReportTree")
    public ResultBean getPlanReportTree(CommonTreeSearch cts) {
        try {
            PlanReportSearch sf = new PlanReportSearch();
            PageResult<PlanReport> pr = getPlanReportData(sf);
            List<TreeBean> list = new ArrayList<TreeBean>();
            List<PlanReport> gtList = pr.getBeanList();
            for (PlanReport gt : gtList) {
                TreeBean tb = new TreeBean();
                tb.setId(gt.getId().toString());
                tb.setText(gt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, cts.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取计划报告树异常",
                    e);
        }
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(PlanReportSearch sf) {
        PageResult<PlanReport> pr = this.getPlanReportData(sf);
        Boolean predict = sf.getPredict();
        if(predict){
            List<PlanReportPredictVo> voList = this.predictPlanReport(pr.getBeanList(),sf.getUserId());
            PageResult<PlanReportPredictVo> result = new PageResult<>();
            result.setBeanList(voList);
            result.setMaxRow(pr.getMaxRow());
            result.setPageSize(pr.getPageSize());
            return callbackDataGrid(result);
        }else{
            return callbackDataGrid(pr);
        }
    }

    /**
     * 预测数据
     * @param list
     * @param userId
     * @return
     */
    private List<PlanReportPredictVo> predictPlanReport(List<PlanReport> list, Long userId){
        List<PlanReportPredictVo> voList = new ArrayList<>();
        for(PlanReport re : list){
            PlanReportPredictVo vo = new PlanReportPredictVo();
            BeanCopy.copyProperties(re,vo);
            //预测
            Map<String,Float> pv = null;
            PlanType planType = re.getUserPlan().getPlanConfig().getPlanType();
            Date bussDay = re.getBussStatDate();
            int score = userScoreHandler.getScore(userId,bussDay);
            int month = DateUtil.getMonth(bussDay)+1;
            if(planType==PlanType.YEAR){
                int dayIndex = DateUtil.getDayOfYear(bussDay);
                pv = reportHandler.predictYearRate(userId,re.getUserPlan().getPlanConfig().getId(),score,dayIndex);
            }else{
                int dayIndex = DateUtil.getDayOfMonth(bussDay);
                pv = reportHandler.predictMonthRate(userId,re.getUserPlan().getPlanConfig().getId(),month,score,dayIndex);
            }
            if(pv!=null){
                vo.setPredictCount(pv.get(MLConstant.PLAN_REPORT_COUNT_LABEL));
                vo.setPredictValue(pv.get(MLConstant.PLAN_REPORT_VALUE_LABEL));
            }
            voList.add(vo);
        }
        return voList;
    }

    private PageResult<PlanReport> getPlanReportData(PlanReportSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        if (StringUtil.isEmpty(sf.getSortField())) {
            Sort s1 = new Sort("bussStatDate", Sort.DESC);
            Sort s2 = new Sort("createdTime", Sort.DESC);
            pr.addSort(s1);
            pr.addSort(s2);
        } else {
            Sort s = new Sort(sf.getSortField(), sf.getSortType());
            pr.addSort(s);
        }
        PageResult<PlanReport> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 手动统计
     *
     * @return
     */
    @RequestMapping(value = "/manualStat", method = RequestMethod.POST)
    public ResultBean manualStat(@RequestBody PlanReportManualStat sf) {
        planService.manualStat(sf);
        return callback(null);
    }

    /**
     * 重新统计
     *
     * @return
     */
    @RequestMapping(value = "/reStat", method = RequestMethod.POST)
    public ResultBean reStat(@RequestBody PlanReportReStat sf) {
        if (sf.getReStatCompareType() == PlanReportReStatCompareType.SPECIFY && sf.getYear() == null) {
            throw new ApplicationException(PmsErrorCode.PLAN_REPORT_RE_STAT_YEAR_NULL);
        }
        //因为支持多个计划报告重新统计，可能会比较费时
        threadPoolHandler.pushThread(() -> {
            Long[] idArr = NumberUtil.stringArrayToLongArray(sf.getIds().split(","));
            for (Long id : idArr){
                planService.reStatPlanReports(id, sf.getReStatCompareType(), sf.getYear());
            }
        });
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        this.deleteUserEntity(beanClass,
                NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")),
                deleteRequest.getUserId());
        return callback(null);
    }

    /**
     * 总统计
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(PlanReportResultGroupStatSearch sf) {
        PlanReportSummaryStat data = new PlanReportSummaryStat();
        //先统计count类型
        List<PlanReportResultGroupStat> list1 = planService.statPlanReportResultGroup(sf, 0);
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("实现情况统计");
        chartPieData.setSubTitle(this.getDateTitle(sf));
        chartPieData.setUnit("次");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("计划次数实现情况统计(次)");
        ChartPieSerieData serieDataCount = new ChartPieSerieData();
        serieDataCount.setName("计划值实现情况统计");
        if (sf.getUserPlanId() != null) {
            UserPlan up = baseService.getObject(UserPlan.class, sf.getUserPlanId());
            chartPieData.setTitle(up.getTitle() + chartPieData.getTitle());
            serieDataCount.setName(serieDataCount.getName() + "(" + up.getPlanConfig().getUnit() + ")");
        }
        for (PlanReportResultGroupStat mp : list1) {
            String name = getStatResultTypeName(mp.getResultType());
            if (name.equals(PlanStatResultType.SKIP.getName())) {
                //该项不统计
                continue;
            }
            chartPieData.getXdata().add(name);
            ChartPieSerieDetailData cp = new ChartPieSerieDetailData();
            cp.setName(name);
            cp.setValue(mp.getTotalCount());
            serieData.getData().add(cp);
        }

        List<PlanReportResultGroupStat> list2 = planService.statPlanReportResultGroup(sf, 1);
        for (PlanReportResultGroupStat mp : list2) {
            String name = getStatResultTypeName(mp.getResultType());
            if (name.equals(PlanStatResultType.SKIP.getName())) {
                //该项不统计
                continue;
            }
            ChartPieSerieDetailData cpCount = new ChartPieSerieDetailData();
            cpCount.setName(name);
            cpCount.setValue(mp.getTotalCount().longValue());
            serieDataCount.getData().add(cpCount);
        }
        chartPieData.getDetailData().add(serieDataCount);
        chartPieData.getDetailData().add(serieData);
        data.setPieData(chartPieData);
        if (sf.getUserPlanId() != null) {
            //右侧统计数据需要直接获取原始的PlanReport
            PageRequest pageRequest = sf.buildQuery();
            pageRequest.setBeanClass(PlanReport.class);
            PageResult<PlanReport> result = baseService.getBeanResult(pageRequest);
            for (PlanReport pr : result.getBeanList()) {
                data.addReportValue(pr.getReportValue());
                data.addReportCountValue(pr.getReportCountValue());
            }
            UserPlanConfigValue upcv = getUserPlanConfigValue(sf.getEndDate(), sf.getUserPlanId());
            data.setUserPlanConfigValue(upcv);
            data.setTotalCount(result.getBeanList().size());
            //计算相差值
            long planCountValueSum = upcv.getPlanCountValue() * data.getTotalCount();
            long planValueSum = upcv.getPlanValue() * data.getTotalCount();
            UserPlan up = baseService.getObject(UserPlan.class, sf.getUserPlanId());
            if (up.getPlanConfig().getCompareType() == CompareType.MORE) {
                data.setDiffCountValueSum(data.getReportCountValueSum() - planCountValueSum);
                data.setDiffValueSum(data.getReportValueSum() - planValueSum);
            } else {
                data.setDiffCountValueSum(data.getReportCountValueSum() - planCountValueSum);
                data.setDiffValueSum(data.getReportValueSum() - planValueSum);
            }
        }
        return callback(data);
    }

    /**
     * 获取配置值
     *
     * @param date
     * @param userPlanId
     * @return
     */
    private UserPlanConfigValue getUserPlanConfigValue(Date date, Long userPlanId) {
        Date compareDate = date;
        if (compareDate == null) {
            compareDate = new Date();
        }
        int year = DateUtil.getYear(compareDate);
        UserPlanConfigValue upcv = planService.getNearestUserPlanConfigValue(year, userPlanId);
        return upcv;
    }

    private String getStatResultTypeName(Short resultType) {
        if (resultType == null) {
            return "未知";
        } else {
            PlanStatResultType ps = PlanStatResultType.getPlanStatResultType(resultType.intValue());
            if (ps == null) {
                return "未知";
            } else {
                return ps.getName();
            }
        }
    }

    /**
     * 清洗数据
     *
     * @return
     */
    @RequestMapping(value = "/cleanData", method = RequestMethod.POST)
    public ResultBean cleanData(@RequestBody @Valid PlanReportDataCleanSearch sf) {
        planService.deletePlanReportData(sf);
        return callback(null);
    }

    /**
     * 计划值推荐
     *
     * @return
     */
    @RequestMapping(value = "/planCommend", method = RequestMethod.GET)
    public ResultBean planCommend(PlanReportPlanCommendSearch sf) {
        //sf.setUserId(this.getCurrentUserId());
        PlanReportPlanCommendDto data = planService.planCommend(sf);
        return callback(data);
    }

    /**
     * 平均值统计
     *
     * @return
     */
    @RequestMapping(value = "/avgStat")
    public ResultBean avgStat(PlanReportAvgStatSearch sf) {

//        if(sf.getUserPlanId()==null){
//            PageResult<PlanReportAvgStat> pr = new PageResult<>();
//            pr.setBeanList(new ArrayList<>());
//            pr.setMaxRow(0L);
//            return callbackDataGrid(pr);
//        }
        //不需要分页
        //sf.setPage(0);
        List<PlanReportAvgStat> list = planService.statPlanReportAvg(sf);
        int id = 1;
        for (PlanReportAvgStat mm : list) {
            //设置planConfig及PlanConfigValue
            UserPlan userPlan = baseService.getObject(UserPlan.class, mm.getUserPlanId().longValue());
            mm.setUserPlan(userPlan);
            UserPlanConfigValue upcv = planService.getNearestUserPlanConfigValue(sf.getYear(), mm.getUserPlanId().longValue());
            mm.setUserPlanConfigValue(upcv);
            mm.setId(id);
            mm.setYear(sf.getYear());
            id++;

        }
        PageResult<PlanReportAvgStat> pr = new PageResult<>();
        pr.setBeanList(list);
        pr.setPageSize(sf.getPageSize());
        pr.setMaxRow(list.size());
        return callbackDataGrid(pr);
    }

    /**
     * 按照日期统计
     *
     * @return
     */
    @RequestMapping(value = "/dateStat")
    public ResultBean dateStat(PlanReportSearch sf) {
        if (sf.getUserPlanId() == null) {
            return callback(new ChartData());
        }
        PageRequest pageRequest = sf.buildQuery();
        pageRequest.setBeanClass(beanClass);
        Sort s = new Sort("bussDay", Sort.ASC);
        pageRequest.addSort(s);
        PageResult<PlanReport> pr = baseService.getBeanResult(pageRequest);
        UserPlan userPlan = baseService.getObject(UserPlan.class, sf.getUserPlanId());
        ChartData chartData = null;
        if (null == sf.getDataStatType() || sf.getDataStatType() == PlanReportDataStatType.ORIGINAL) {
            chartData = createOriginalStatChartData(pr.getBeanList(), userPlan, sf);
        } else {
            chartData = createPercentStatChartData(pr.getBeanList(), userPlan, sf);
        }
        return callback(chartData);

    }

    /**
     * 原始数据类型
     *
     * @param list
     * @param userPlan
     * @return
     */
    private ChartData createOriginalStatChartData(List<PlanReport> list, UserPlan userPlan, PlanReportSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle(userPlan.getTitle() + "执行统计");
        chartData.setLegendData(new String[]{"值(" + userPlan.getPlanConfig().getUnit() + ")","次数"});
        //混合图形下使用
        chartData.addYAxis("数值",userPlan.getPlanConfig().getUnit());
        chartData.addYAxis("次数","次");
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName("值(" + userPlan.getPlanConfig().getUnit() + ")");

        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        BigDecimal totalValue = new BigDecimal(0);
        for (PlanReport bean : list) {
            chartData.getIntXData().add(bean.getBussDay());
            chartData.getXdata().add(bean.getBussDay().toString());
            yData1.getData().add(bean.getReportCountValue());
            yData2.getData().add(bean.getReportValue());
            totalCount = totalCount.add(new BigDecimal(bean.getReportCountValue()));
            totalValue = totalValue.add(new BigDecimal(bean.getReportValue()));
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        String totalString = totalCount.longValue() + "(次)," + totalValue.doubleValue() + "(" + userPlan.getPlanConfig().getUnit() + ")";
        chartData.setSubTitle(this.getDateTitle(sf, totalString));
        chartData = ChartUtil.completeDate(chartData, sf);
        return chartData;
    }

    /**
     * 百分比数据类型
     *
     * @param list
     * @param userPlan
     * @return
     */
    private ChartData createPercentStatChartData(List<PlanReport> list, UserPlan userPlan, PlanReportSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle(userPlan.getTitle() + "执行统计(百分比)");
        chartData.setLegendData(new String[]{"次数", "值(" + userPlan.getPlanConfig().getUnit() + ")"});
        ChartYData yData1 = new ChartYData();
        yData1.setName("次数");
        ChartYData yData2 = new ChartYData();
        yData2.setName("值(" + userPlan.getPlanConfig().getUnit() + ")");
        //总的值
        BigDecimal totalCount = new BigDecimal(0);
        BigDecimal totalValue = new BigDecimal(0);
        for (PlanReport bean : list) {
            chartData.getIntXData().add(bean.getBussDay());
            chartData.getXdata().add(bean.getBussDay().toString());
            yData1.getData().add(NumberUtil.getPercentValue(bean.getReportCountValue(), bean.getPlanCountValue(), 1));
            yData2.getData().add((NumberUtil.getPercentValue(bean.getReportValue(), bean.getPlanValue(), 1)));
            totalCount = totalCount.add(new BigDecimal(bean.getReportCountValue()));
            totalValue = totalValue.add(new BigDecimal(bean.getReportValue()));
        }
        chartData.getYdata().add(yData1);
        chartData.getYdata().add(yData2);
        String totalString = totalCount.longValue() + "(次)," + totalValue.doubleValue() + "(" + userPlan.getPlanConfig().getUnit() + ")";
        chartData.setSubTitle(this.getDateTitle(sf, totalString));
        chartData = ChartUtil.completeDate(chartData, sf);
        return chartData;
    }

    /**
     * 时间线统计
     *
     * @return
     */
    @RequestMapping(value = "/timelineStat")
    public ResultBean timelineStat(@Valid PlanReportTimelineStatSearch sf) {
        Date bussDay = null;
        PeriodType period = null;
        if(sf.getDateGroupType()==DateGroupType.YEAR){
            period = PeriodType.YEARLY;
            bussDay = DateUtil.getDate(sf.getYear() + "-01" + "-01", DateUtil.FormatDay1);
        }else{
            period = PeriodType.MONTHLY;
            bussDay = DateUtil.getDate(sf.getYear() + "-" + sf.getMonth() + "-01", DateUtil.FormatDay1);
        }
        Long userId = sf.getUserId();
        int totalDays =0;
        //获取评分使用
        Date startDate;
        Date endDate;
        int month = DateUtil.getMonth(bussDay);
        if (PeriodType.YEARLY == period) {
            totalDays = DateUtil.getYearDays(bussDay);
            startDate = DateUtil.getYearFirst(bussDay);
            endDate = DateUtil.getLastDayOfYear(bussDay);
        }else{
            totalDays = DateUtil.getMonthDays(bussDay);
            startDate = DateUtil.getFirstDayOfMonth(bussDay);
            endDate = DateUtil.getLastDayOfMonth(bussDay);
        }
        List<PlanReportTimeline> list = planService.getPlanReportTimelineList(startDate, endDate, sf.getUserPlanId());
        ChartData chartData = new ChartData();
        PlanReportTimeline pr0 = list.get(0);
        UserPlan userPlan = pr0.getUserPlan();
        chartData.setTitle(userPlan.getTitle() + "统计");
        chartData.setUnit("%");
        chartData.setSubTitle("计划次数:" + pr0.getPlanCountValue() + ",计划值:" + pr0.getPlanValue() + "(" + userPlan.getPlanConfig().getUnit() + ")");
        List<String> legends = new ArrayList<>();
        legends.add("计划次数完成进度(%)");
        legends.add("计划值完成进度(%)");
        legends.add("时间进度(%)");
        ChartYData countData = new ChartYData();
        countData.setName("计划次数完成进度(%)");
        ChartYData valueData = new ChartYData();
        valueData.setName("计划值完成进度(%)");
        ChartYData timeData = new ChartYData();
        timeData.setName("时间进度(%)");
        boolean predict = sf.getPredict();
        Map<String, Integer> scoreMap = null;
        ChartYData predictCountData = new ChartYData();
        ChartYData predictValueData = new ChartYData();
        if(predict){
            scoreMap = userScoreHandler.getUserScoreMap(userId,startDate,endDate,period);
            legends.add("次数预测值(%)");
            predictCountData.setName("次数预测值(%)");
            legends.add("值预测值(%)");
            predictValueData.setName("值预测值(%)");
        }
        chartData.setLegendData(legends.toArray(new String[legends.size()]));
        if (list.isEmpty()) {
            return callback(chartData);
        }
        //缓存计划报告
        Map<String,PlanReportTimeline> prMap = new HashMap<>();
        PlanType planType = pr0.getUserPlan().getPlanConfig().getPlanType();
        for (PlanReportTimeline tl : list){
            int passDays=0;
            if(planType == PlanType.YEAR){
                passDays = DateUtil.getDayOfYear(tl.getBussStatDate());
            }else{
                passDays = DateUtil.getDayOfMonth(tl.getBussStatDate());
            }
            prMap.put(passDays+"",tl);
        }
        PlanReportTimeline lastPr = list.get(list.size()-1);
        //需要以完整的天数为准，因为BudgetTimeline有可能缺失，而且如果是当月的，后续数据也不全
        for(int i=1;i<=totalDays;i++){
            String key = i+"";
            if (period == PeriodType.MONTHLY) {
                chartData.getXdata().add(i + "号");
            } else {
                Date date = DateUtil.getDate(i-1,bussDay);
                chartData.getXdata().add(DateUtil.getFormatDate(date, "MM-dd"));
            }
            chartData.getIntXData().add(i);
            PlanReportTimeline pr = prMap.get(key);
            double rate = NumberUtil.getPercentValue(i, totalDays, 0);
            if(pr==null){
                countData.getData().add(null);
                valueData.getData().add(null);
            }else{
                double planCountRate = NumberUtil.getPercentValue(pr.getReportCountValue(), pr.getPlanCountValue(), 2);
                double planValueRate = NumberUtil.getPercentValue(pr.getReportValue(), pr.getPlanValue(), 2);
                countData.getData().add(planCountRate);
                valueData.getData().add(planValueRate);
            }
            timeData.getData().add(rate);
            if(predict){
                Integer score = scoreMap.get(key);
                if(score==null){
                    //取默认的最后一天的
                    score = scoreMap.get("0");
                }
                long planConfigId = pr.getUserPlan().getPlanConfig().getId();
                Map<String,Float> predictValue = null;
                if (period == PeriodType.MONTHLY) {
                    predictValue = reportHandler.predictMonthRate(userId,planConfigId,month,score,i);
                }else{
                    predictValue = reportHandler.predictYearRate(userId,planConfigId,score,i);
                }
                predictCountData.getData().add(NumberUtil.getDoubleValue(predictValue.get(MLConstant.PLAN_REPORT_COUNT_LABEL)*100,0));
                predictValueData.getData().add(NumberUtil.getDoubleValue(predictValue.get(MLConstant.PLAN_REPORT_VALUE_LABEL)*100,0));

            }
        }
        chartData.getYdata().add(countData);
        chartData.getYdata().add(valueData);
        chartData.getYdata().add(timeData);
        //预测
        if(predict){
            chartData.getYdata().add(predictCountData);
            chartData.getYdata().add(predictValueData);
        }
        return callback(chartData);
    }

    /**
     * 重新统计时间线
     *
     * @return
     */
    @RequestMapping(value = "/reStatTimeline", method = RequestMethod.POST)
    public ResultBean reStatTimeline(@RequestBody @Valid PlanReportReStatTimelineRequest re) {
        Date firstDay;
        Date lastDay;
        if (re.getDateGroupType() == DateGroupType.MONTH) {
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
        List<PlanReportTimeline> datas = new ArrayList<>();
        Date cc = firstDay;
        UserPlan userPlan = this.getUserEntity(UserPlan.class, re.getUserPlanId(), re.getUserId());
        while (!cc.after(lastDay)) {
            PlanReport planReport = planService.statPlanReport(userPlan, cc, userPlan.getUserId(), PlanReportDataStatFilterType.ORIGINAL);
            if (planReport == null) {
                continue;
            }
            PlanReportTimeline timeline = new PlanReportTimeline();
            BeanCopy.copyProperties(planReport, timeline);
            //需要设置为该天的
            timeline.setBussStatDate(cc);
            datas.add(timeline);
            cc = DateUtil.getDate(1, cc);
        }
        if(datas.size()>0){
            Integer bussDay = datas.get(0).getBussDay();
            planService.reSavePlanReportTimeline(datas,bussDay,re.getUserId(),re.getUserPlanId());
            return callback(null);
        }else{
            return callbackErrorInfo("无相关统计数据");
        }
    }
}
