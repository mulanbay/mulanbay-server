package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.persistent.domain.Dream;
import cn.mulanbay.pms.persistent.domain.DreamRemind;
import cn.mulanbay.pms.persistent.domain.PlanReport;
import cn.mulanbay.pms.persistent.domain.UserPlan;
import cn.mulanbay.pms.persistent.dto.DreamStat;
import cn.mulanbay.pms.persistent.enums.ChartType;
import cn.mulanbay.pms.persistent.enums.DreamStatus;
import cn.mulanbay.pms.persistent.enums.PlanReportDataStatFilterType;
import cn.mulanbay.pms.persistent.service.DreamService;
import cn.mulanbay.pms.persistent.service.PlanService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.dream.*;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 梦想
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/dream")
public class DreamController extends BaseController {

    @Autowired
    DreamService dreamService;

    @Autowired
    PlanService planService;

    @Autowired
    CacheHandler cacheHandler;

    private static Class<Dream> beanClass = Dream.class;

    /**
     * 获取梦想列表
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(DreamSearch sf) {
        //sf.setUserId(this.getCurrentUserId());
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort(sf.getSortField(), sf.getSortType());
        pr.addSort(s);
        PageResult<Dream> qr = baseService.getBeanResult(pr);
        this.request.setAttribute("endDate", DateUtil.getLastDayOfCurrYear());
        return callbackDataGrid(qr);
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid DreamFormRequest formRequest) {
        Dream bean = new Dream();
        BeanCopy.copyProperties(formRequest, bean);
        if (formRequest.getUserPlanId() != null) {
            UserPlan userPlan = this.getUserEntity(UserPlan.class, formRequest.getId(), formRequest.getUserId());
            bean.setUserPlan(userPlan);
            if (bean.getPlanValue() == null) {
                throw new ApplicationException(PmsErrorCode.DREAM_PLAN_VALUE_NULL);
            }
        }
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
        Dream bean = this.getUserEntity(beanClass, getRequest.getId(), getRequest.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid DreamFormRequest formRequest) {
        if (formRequest.getStatus() == DreamStatus.FINISHED && formRequest.getFinishedDate() == null) {
            return callbackErrorCode(PmsErrorCode.DREAM_FININSH_DATE_NULL);
        }
        Dream dbData = this.getUserEntity(beanClass, formRequest.getId(), formRequest.getUserId());
        dbData.setLastModifyTime(new Date());
        long daysChange = formRequest.getProposedDate().getTime() - dbData.getProposedDate().getTime();
        if (daysChange > 0) {
            //延期
            int delays = dbData.getDelays() == null ? 0 : dbData.getDelays();
            dbData.setDelays(delays + 1);
        }
        String s = dbData.getDateChangeHistory() == null ? "" : dbData.getDateChangeHistory();
        if (s.isEmpty()) {
            s += DateUtil.getFormatDate(dbData.getProposedDate(), DateUtil.FormatDay1);
        }
        s += "-->";
        s += DateUtil.getFormatDate(formRequest.getProposedDate(), DateUtil.FormatDay1);
        dbData.setDateChangeHistory(s);
        if (formRequest.getUserPlanId() != null) {
            UserPlan userPlan = this.getUserEntity(UserPlan.class, formRequest.getUserPlanId(), formRequest.getUserId());
            dbData.setUserPlan(userPlan);
            if (dbData.getPlanValue() == null) {
                return callbackErrorCode(PmsErrorCode.DREAM_PLAN_VALUE_NULL);
            }
        }
        BeanCopy.copyProperties(formRequest, dbData);
        baseService.updateObject(dbData);
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
     * 列表页面统计进入
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(DreamStatSearch sf, @RequestParam(value = "statuses[]", required = false) List<DreamStatus> statuses) {
        sf.setInStatuses(statuses);
        PageRequest pr = sf.buildQuery();
        DreamStatListSearch dreamStatSearch = new DreamStatListSearch();
        dreamStatSearch.setGroupType(DreamStatListSearch.GroupType.STATUS);
        dreamStatSearch.setChartType(ChartType.PIE);
        return getChartData(pr, dreamStatSearch);
    }

    /**
     * 统计页面进入
     *
     * @return
     */
    @RequestMapping(value = "/statList", method = RequestMethod.GET)
    public ResultBean statList(DreamStatListSearch sf) {
        PageRequest pr = sf.buildQuery();
        return getChartData(pr, sf);
    }

    private ResultBean getChartData(PageRequest pr, DreamStatListSearch sf) {
        List<DreamStat> list = dreamService.getDreamStat(pr, sf.getGroupType());
        if (sf.getChartType() == ChartType.PIE) {
            return callback(createStatPieData(list, sf));
        } else {
            return callback(createStatBarData(list, sf));
        }
    }

    /**
     * 饼图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartPieData createStatPieData(List<DreamStat> list, DreamStatListSearch sf) {
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("梦想统计");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("梦想");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (DreamStat bean : list) {
            chartPieData.getXdata().add(bean.getName());
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(getStatName(sf.getGroupType(), bean));
            dataDetail.setValue(bean.getTotalCount().intValue());
            serieData.getData().add(dataDetail);
            totalValue = totalValue.add(new BigDecimal(bean.getTotalCount().intValue()));
        }
        String subTitle = this.getDateTitle(sf, String.valueOf(totalValue.longValue()) + "个");
        chartPieData.setSubTitle(subTitle);
        chartPieData.getDetailData().add(serieData);
        return chartPieData;
    }

    /**
     * 柱状图数据
     *
     * @param list
     * @param sf
     * @return
     */
    private ChartData createStatBarData(List<DreamStat> list, DreamStatListSearch sf) {
        ChartData chartData = new ChartData();
        chartData.setTitle("梦想统计");
        chartData.setLegendData(new String[]{"类别"});
        ChartYData yData = new ChartYData();
        yData.setName("梦想");
        //总的值
        BigDecimal totalValue = new BigDecimal(0);
        for (DreamStat bean : list) {
            chartData.getXdata().add(getStatName(sf.getGroupType(), bean));
            yData.getData().add(bean.getTotalCount().intValue());
            totalValue = totalValue.add(new BigDecimal(bean.getTotalCount().intValue()));
        }
        chartData.getYdata().add(yData);
        String subTitle = this.getDateTitle(sf, String.valueOf(totalValue.longValue()) + "个");
        chartData.setSubTitle(subTitle);
        return chartData;

    }

    private String getStatName(DreamStatListSearch.GroupType groupType, DreamStat dd) {
        if (groupType == DreamStatListSearch.GroupType.DIFFICULTY) {
            return "困难等级(" + dd.getDifficulty() + ")";
        } else if (groupType == DreamStatListSearch.GroupType.IMPORTANTLEVEL) {
            return "重要等级(" + dd.getImportantLevel().doubleValue() + ")";
        } else if (groupType == DreamStatListSearch.GroupType.STATUS) {
            return "状态(" + DreamStatus.getDreamStatus(dd.getStatus()).getName() + ")";
        } else {
            return null;
        }
    }

    /**
     * 刷新进度（针对有关联计划配置的）
     *
     * @return
     */
    @RequestMapping(value = "/refreshRate", method = RequestMethod.GET)
    public ResultBean refreshRate() {
        Long userId = this.getCurrentUserId();
        List<Dream> dreams = dreamService.getNeedRefreshRateDream(userId);
        int n = 0;
        Date now = new Date();
        for (Dream d : dreams) {
            UserPlan pc = d.getUserPlan();
            PlanReport report = planService.statPlanReport(pc, now, userId, PlanReportDataStatFilterType.NO_DATE);
            long v = report.getReportValue();
            double rate = NumberUtil.getPercentValue(v, d.getPlanValue(), 0);
            d.setRate((int) rate);
            if (rate == 0) {
                d.setStatus(DreamStatus.CREATED);
            } else if (rate < 100) {
                d.setStatus(DreamStatus.STARTED);
            } else {
                d.setStatus(DreamStatus.FINISHED);
            }
            baseService.updateObject(d);
            n++;
        }
        return callback("更新了" + n + "个梦想");
    }


    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/getRemind", method = RequestMethod.GET)
    public ResultBean getRemind(@Valid CommonBeanGetRequest getRequest) {
        DreamRemind dreamRemind = dreamService.getRemindByDream(getRequest.getId(), getRequest.getUserId());
        return callback(dreamRemind);
    }

    /**
     * 增加/修改用户计划提醒
     *
     * @return
     */
    @RequestMapping(value = "/addOrEditRemind", method = RequestMethod.POST)
    public ResultBean addOrEditRemind(@RequestBody @Valid DreamRemindFormRequest formRequest) {
        DreamRemind bean = null;
        if (formRequest.getId() != null) {
            bean = this.getUserEntity(DreamRemind.class, formRequest.getId(), formRequest.getUserId());
            BeanCopy.copyProperties(formRequest, bean);
            Dream dream = this.getUserEntity(Dream.class, formRequest.getDreamId(), formRequest.getUserId());
            bean.setDream(dream);
            bean.setLastModifyTime(new Date());
            baseService.updateObject(bean);
            //只要修改过重新开始计算提醒
            cacheHandler.delete("dreamNotify:" + this.getCurrentUserId() + ":" + bean.getDream().getId());
        } else {
            bean = new DreamRemind();
            BeanCopy.copyProperties(formRequest, bean);
            Dream dream = this.getUserEntity(Dream.class, formRequest.getDreamId(), formRequest.getUserId());
            bean.setDream(dream);
            bean.setCreatedTime(new Date());
            baseService.saveObject(bean);
        }
        return callback(null);
    }

}
