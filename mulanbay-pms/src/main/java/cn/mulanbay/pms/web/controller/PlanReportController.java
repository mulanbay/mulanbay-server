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
import cn.mulanbay.pms.common.PmsErrorCode;
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
        return callbackDataGrid(getPlanReportData(sf));
    }

    private PageResult<PlanReport> getPlanReportData(PlanReportSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        if (StringUtil.isEmpty(sf.getSortField())) {
            Sort s = new Sort("bussStatDate", Sort.DESC);
            pr.addSort(s);
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
        planService.reStatPlanReports(sf.getIds(), sf.getReStatCompareType(), sf.getYear());
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
        Date startDate = null;
        Date endDate = null;
        if (sf.getDateGroupType() == DateGroupType.MONTH) {
            startDate = DateUtil.getDate(sf.getYear() + "-" + sf.getMonth() + "-01", DateUtil.FormatDay1);
            endDate = DateUtil.getLastDayOfMonth(startDate);        //界面传过来的只有开始时间，需要转换为当月第一天及最后一天
        } else {
            startDate = DateUtil.getDate(sf.getYear() + "-01" + "-01", DateUtil.FormatDay1);
            endDate = DateUtil.getLastDayOfYear(sf.getYear());
        }
        List<PlanReportTimeline> list = planService.getPlanReportTimelineList(startDate, endDate, sf.getUserPlanId());
        ChartData chartData = new ChartData();
        if (!list.isEmpty()) {
            PlanReportTimeline pr0 = list.get(0);
            UserPlan userPlan = pr0.getUserPlan();
            chartData.setTitle(userPlan.getTitle() + "统计");
            chartData.setSubTitle("计划次数:" + pr0.getPlanCountValue() + ",计划值:" + pr0.getPlanValue() + "(" + userPlan.getPlanConfig().getUnit() + ")");
            chartData.setLegendData(new String[]{"计划次数完成进度(%)", "计划值完成进度(%)", "时间进度(%)"});
            ChartYData countData = new ChartYData();
            countData.setName("计划次数完成进度(%)");
            ChartYData valueData = new ChartYData();
            valueData.setName("计划值完成进度(%)");
            ChartYData timeData = new ChartYData();
            timeData.setName("时间进度(%)");
            for (PlanReportTimeline tl : list) {
                int dayIndex = 0;
                int totalDays = 0;
                if (sf.getDateGroupType() == DateGroupType.MONTH) {
                    dayIndex = DateUtil.getDayOfMonth(tl.getBussStatDate());
                    totalDays = DateUtil.getMonthDays(tl.getBussStatDate());
                    chartData.getXdata().add(dayIndex + "号");
                } else {
                    dayIndex = DateUtil.getDayOfYear(tl.getBussStatDate());
                    totalDays = DateUtil.getYearDays(tl.getBussStatDate());
                    chartData.getXdata().add(DateUtil.getFormatDate(tl.getBussStatDate(), "MM-dd"));
                }
                chartData.getIntXData().add(dayIndex);
                double planCountRate = NumberUtil.getPercentValue(tl.getReportCountValue(), tl.getPlanCountValue(), 2);
                double planValueRate = NumberUtil.getPercentValue(tl.getReportValue(), tl.getPlanValue(), 2);
                double rate = NumberUtil.getPercentValue(dayIndex, totalDays, 0);
                countData.getData().add(planCountRate);
                valueData.getData().add(planValueRate);
                timeData.getData().add(rate);
            }
            chartData.getYdata().add(countData);
            chartData.getYdata().add(valueData);
            chartData.getYdata().add(timeData);

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
