package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.UserBehavior;
import cn.mulanbay.pms.persistent.domain.UserBehaviorConfig;
import cn.mulanbay.pms.persistent.dto.UserBehaviorDataStat;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.enums.DateGroupType;
import cn.mulanbay.pms.persistent.service.DataService;
import cn.mulanbay.pms.persistent.service.UserBehaviorService;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.CommonBeanGetRequest;
import cn.mulanbay.pms.web.bean.request.data.UserBehaviorCalendarStatSearch;
import cn.mulanbay.pms.web.bean.request.data.UserBehaviorCompareSearch;
import cn.mulanbay.pms.web.bean.request.data.UserBehaviorHourStatSearch;
import cn.mulanbay.pms.web.bean.request.data.UserBehaviorStatSearch;
import cn.mulanbay.pms.web.bean.request.userbehavior.*;
import cn.mulanbay.pms.web.bean.response.DataGrid;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.pms.web.bean.response.behavior.BehaviorCalendarVo;
import cn.mulanbay.pms.web.bean.response.chart.*;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 用户行为
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/userBehavior")
public class UserBehaviorController extends BaseController {

    private static Class<UserBehavior> beanClass = UserBehavior.class;

    @Autowired
    UserBehaviorService userBehaviorService;

    @Autowired
    DataService dataService;

    /**
     * 获取用户行为配置模板树
     * 筛选用户等级
     *
     * @return
     */
    @RequestMapping(value = "/getUserBehaviorConfigTree")
    public ResultBean getUserBehaviorConfigTree(UserBehaviorConfigToUserTreeSearch sf) {
        try {
            List<TreeBean> result = dataService.getUserBehaviorConfigTree(sf.getLevel(), CommonStatus.ENABLE);
            return callback(TreeBeanUtil.addRoot(result, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户行为配置模板树异常",
                    e);
        }
    }

    /**
     * 获取用户行为树
     *
     * @return
     */
    @RequestMapping(value = "/getUserBehaviorTree")
    public ResultBean getUserBehaviorTree(UserBehaviorTreeSearch sf) {
        try {
            sf.setStatus(CommonStatus.ENABLE);
            PageRequest pr = sf.buildQuery();
            pr.setBeanClass(beanClass);
            List<UserBehavior> ubcs = baseService.getBeanList(pr);
            Map<String, List<UserBehavior>> map = this.changeToUserBehaviorMap(ubcs);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (String key : map.keySet()) {
                TreeBean tb = new TreeBean();
                tb.setId("P_" + key);
                tb.setText(key);

                List<UserBehavior> ll = map.get(key);
                for (UserBehavior nc : ll) {
                    TreeBean child = new TreeBean();
                    child.setId(nc.getId().toString());
                    child.setText(nc.getTitle());
                    tb.addChild(child);
                }
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户行为配置模板树异常",
                    e);
        }
    }

    private Map<String, List<UserBehavior>> changeToUserBehaviorMap(List<UserBehavior> gtList) {
        Map<String, List<UserBehavior>> map = new TreeMap<>();
        for (UserBehavior nc : gtList) {
            List<UserBehavior> list = map.get(nc.getUserBehaviorConfig().getBehaviorType().getName());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(nc);
            map.put(nc.getUserBehaviorConfig().getBehaviorType().getName(), list);
        }
        return map;
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(UserBehaviorSearch sf) {
        PageResult<UserBehavior> pr = getUserBehaviorData(sf);
        return callbackDataGrid(pr);
    }

    private PageResult<UserBehavior> getUserBehaviorData(UserBehaviorSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<UserBehavior> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid UserBehaviorCreateRequest formRequest) {
        UserBehavior bean = new UserBehavior();
        UserBehaviorConfig unc = userBehaviorService.getUserBehaviorConfig(formRequest.getUserBehaviorConfigId(), formRequest.getLevel());
        if (unc == null) {
            return callbackErrorCode(PmsErrorCode.USER_BEHAVIOR_CONFIG_WITH_LEVEL_NOT_FOUND);
        }
        bean.setUserBehaviorConfig(unc);
        BeanCopy.copyProperties(formRequest, bean);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(bean);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(@Valid CommonBeanGetRequest ubg) {
        UserBehavior bean = baseService.getObjectWithUser(beanClass, ubg.getId(), ubg.getUserId());
        return callback(bean);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid UserBehaviorEditRequest formRequest) {
        UserBehavior bean = baseService.getObjectWithUser(beanClass, formRequest.getId(), formRequest.getUserId());
        //不支持修改模板
        BeanCopy.copyProperties(formRequest, bean);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(bean);
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
     * 用户行为统计（小时点统计）
     * 比如统计用户在14-15点都做了什么
     *
     * @return
     */
    @RequestMapping(value = "/hourStat", method = RequestMethod.GET)
    public ResultBean hourStat(@Valid UserBehaviorHourStatSearch sf) {
        List<UserBehaviorDataStat> list = userBehaviorService.statHourUserBehaviorData(sf.getStartDate(), sf.getEndDate(),
                sf.getUserId(), sf.getMinHour(), sf.getMaxHour());
        ChartPieData chartPieData = new ChartPieData();
        chartPieData.setTitle("用户行为分析");
        chartPieData.setUnit("次");
        ChartPieSerieData serieData = new ChartPieSerieData();
        serieData.setName("次数");
        serieData.setUnit("次");
        Map<String, Integer> map = new HashMap<>();
        for (UserBehaviorDataStat bean : list) {
            Integer n = map.get(bean.getName());
            //查询出来的数据是单条的数值，因此次数就是1
            //int v = Integer.valueOf(bean.getValue().toString());
            int v = 1;
            if (n == null) {
                map.put(bean.getName(), v);
            } else {
                map.put(bean.getName(), v + n);
            }
        }
        for (String key : map.keySet()) {
            chartPieData.getXdata().add(key);
            ChartPieSerieDetailData dataDetail = new ChartPieSerieDetailData();
            dataDetail.setName(key);
            dataDetail.setValue(map.get(key));
            serieData.getData().add(dataDetail);
        }
        chartPieData.getDetailData().add(serieData);
        return callback(chartPieData);
    }

    /**
     * 用户行为统计（带饼图分析）
     *
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ResultBean stat(@Valid UserBehaviorStatSearch sf) {
        if (sf.getDataType() != null) {
            return callback(this.createDayStatByData(sf));
        } else if (sf.getDateGroupType() == DateGroupType.DAY) {
            return callback(this.createDayStat(sf));
        } else if (sf.getDateGroupType() == DateGroupType.MONTH) {
            return callback(this.createMonthStat(sf));
        } else if (sf.getDateGroupType() == DateGroupType.TIMELINE) {
            //一天的详情，即时间线，目前是手机端使用
            return callback(this.getFullDayStatData(sf));
        } else {
            return callback(this.createYearStat(sf));
        }
    }

    /**
     * 用户行为统计（日历模式）
     *
     * @return
     */
    @RequestMapping(value = "/calendarStat", method = RequestMethod.GET)
    public ResultBean calendarStat(@Valid UserBehaviorCalendarStatSearch sf) {
        List<UserBehaviorDataStat> list = userBehaviorService.statUserBehaviorData(sf.getBehaviorType(), sf.getStartDate(), sf.getEndDate(),
                sf.getUserId(), sf.getName(), null, true, sf.getUserBehaviorId());
        List<BehaviorCalendarVo> res = new ArrayList<>();
        long id = 0;
        for (UserBehaviorDataStat stat : list) {
            BehaviorCalendarVo uc = new BehaviorCalendarVo();
            uc.setId(id++);
            uc.setReadOnly(true);
            uc.setTitle(stat.getName());
            uc.setContent(stat.getName());
            uc.setDelayCounts(0);
            uc.setBehaviorType(stat.getBehaviorType());
            uc.setBussDay(stat.getDate());
            if (stat.getDateRegion()) {
                uc.setAllDay(true);
                uc.setExpireTime(DateUtil.getDate(Integer.valueOf(stat.getValue().toString()), stat.getDate()));
            } else {
                uc.setAllDay(false);
                uc.setExpireTime(stat.getDate());
            }
            res.add(uc);
        }
        return callback(res);
    }

    private DataGrid getFullDayStatData(UserBehaviorStatSearch sf) {
        //该天之前的就可以
        Date[] dd = getStatDateRange(DateGroupType.DAY, sf.getDate());
        List<UserBehaviorDataStat> list = userBehaviorService.statUserBehaviorData(sf.getBehaviorType(),
                dd[0], dd[1], sf.getUserId(), sf.getName(), null, true, sf.getUserBehaviorId());
        Collections.sort(list, (UserBehaviorDataStat b1, UserBehaviorDataStat b2) -> b2.getDate().compareTo(b1.getDate()));
        DataGrid dg = new DataGrid();
        dg.setPage(1);
        dg.setTotal(0);
        dg.setRows(list);
        return dg;
    }

    /**
     * 散点图
     *
     * @param sf
     * @return
     */
    private ScatterChartData createDayStatByData(UserBehaviorStatSearch sf) {
        ScatterChartData chartData = new ScatterChartData();
        double av = 0.0;
        if (sf.getDataType() == DateGroupType.DAYOFMONTH) {
            chartData.setxUnit("号");
            av = 15;
        } else if (sf.getDataType() == DateGroupType.DAYOFWEEK) {
            chartData.setxUnit("星期");
            av = 3.5;
        } else if (sf.getDataType() == DateGroupType.DAY) {
            chartData.setxUnit("");
            av = 183;
        }
        chartData.setyUnit("点");
        DateGroupType dateGroupType = sf.getDateGroupType();
        String titlePre = "";
        if (dateGroupType == DateGroupType.DAY) {
            titlePre = DateUtil.getFormatDate(sf.getDate(), DateUtil.FormatDay1);
        } else if (dateGroupType == DateGroupType.MONTH) {
            titlePre = DateUtil.getFormatDate(sf.getDate(), "yyyy年MM月");
        } else if (dateGroupType == DateGroupType.YEAR) {
            titlePre = DateUtil.getFormatDate(sf.getDate(), "yyyy年");
        }
        chartData.setTitle(titlePre + "用户行为分析");
        Date[] dd = getStatDateRange(dateGroupType, sf.getDate());
        List<UserBehaviorDataStat> list = userBehaviorService.statUserBehaviorData(sf.getBehaviorType(),
                dd[0], dd[1], sf.getUserId(), sf.getName(), null, null, sf.getUserBehaviorId());
        Map<String, ScatterChartDetailData> map = new HashMap<>();
        for (UserBehaviorDataStat bean : list) {
            int xv = 0;
            if (sf.getDataType() == DateGroupType.DAYOFMONTH) {
                xv = DateUtil.getDayOfMonth(bean.getDate());
            } else if (sf.getDataType() == DateGroupType.DAYOFWEEK) {
                xv = DateUtil.getDayIndexInWeek(bean.getDate());
            } else if (sf.getDataType() == DateGroupType.DAY) {
                xv = DateUtil.getDayOfYear(bean.getDate());
            }
            //精确到分钟，这样可以知道是什么区间
            String yvh = DateUtil.getFormatDate(bean.getDate(), "HH");
            String yvm = DateUtil.getFormatDate(bean.getDate(), "mm");
            double ms = (Integer.valueOf(yvh) * 60 + Integer.valueOf(yvm)) / 60.0;
            String yv = PriceUtil.changeToString(0, ms);
            String name = bean.getName();
            ScatterChartDetailData detailData = map.get(name);
            if (detailData == null) {
                detailData = new ScatterChartDetailData();
                detailData.setName(name);
                detailData.setxAxisAverage(av);
                map.put(name, detailData);
            }
            detailData.appendData(xv, yv, 1);
        }
        for (String k : map.keySet()) {
            chartData.addLegent(k);
            chartData.addSeriesData(map.get(k));
        }
        return chartData;
    }


    /**
     * 天的以极坐标的图像显示
     *
     * @param sf
     * @return
     */
    private ChartPolarBarData createDayStat(UserBehaviorStatSearch sf) {
        ChartPolarBarData polarBarData = new ChartPolarBarData();
        polarBarData.setTitle("用户行为分析");
        for (int i = 0; i < 24; i++) {
            //24小时
            polarBarData.getXdata().add(i + "-" + (i + 1) + "点");
        }
        Date[] dd = getStatDateRange(sf.getDateGroupType(), sf.getDate());
        List<UserBehaviorDataStat> list = userBehaviorService.statUserBehaviorData(sf.getBehaviorType(),
                dd[0], dd[1], sf.getUserId(), sf.getName(), null, true, sf.getUserBehaviorId());
        for (UserBehaviorDataStat stat : list) {
            String hour = DateUtil.getFormatDate(stat.getDate(), "HH");
            int ih = Integer.valueOf(hour);
            int index = polarBarData.getLegendIndex(stat.getName());
            if (index < 0) {
                //没有
                polarBarData.getLegendData().add(stat.getName());
                ChartYData yData = new ChartYData();
                yData.setName(stat.getName());
                yData.setStack("a");
                List data = new ArrayList<>();
                for (int i = 0; i < 24; i++) {
                    if (i == ih) {
                        data.add(1);
                    } else {
                        data.add(0);
                    }
                }
                yData.setData(data);
                polarBarData.getYdata().add(yData);
            } else {
                ChartYData yData = polarBarData.getYdata().get(index);
                //Object o = yData.getData().get(ih);
                //Integer v = 1+Integer.valueOf(o.toString());
                //都只显示为一次，否则比较难看
                yData.getData().set(ih, 1);
            }
        }
        return polarBarData;
    }

    /**
     * 月的以日历饼图显示
     *
     * @param sf
     * @return
     */
    private ChartCalendarPieData createMonthStat(UserBehaviorStatSearch sf) {
        Date[] dd = getStatDateRange(sf.getDateGroupType(), sf.getDate());
        //界面传过来的只有开始时间，需要转换为当月第一天及最后一天
        ChartCalendarPieData pieData = new ChartCalendarPieData(sf.getBehaviorType());
        pieData.setUnit("次");
        String monthString = DateUtil.getFormatDate(dd[0], "yyyy年MM月");
        pieData.setTitle(monthString + "用户行为分析");
        //子标题
        pieData.setStartDate(dd[0]);
        List<UserBehaviorDataStat> list = userBehaviorService.statUserBehaviorData(sf.getBehaviorType(), dd[0], dd[1], sf.getUserId(), sf.getName(), true, null, sf.getUserBehaviorId());
        for (UserBehaviorDataStat stat : list) {
            pieData.addData(DateUtil.getFormatDate(stat.getDate(), DateUtil.FormatDay1), stat.getName(), stat.getValue(), stat.getDateRegion(), Integer.valueOf(stat.getValue().toString()), false);
        }
        return pieData;
    }

    /**
     * 年的以日历图显示
     *
     * @param sf
     * @return
     */
    private ChartCalendarData createYearStat(UserBehaviorStatSearch sf) {
        if (sf.getUserBehaviorId() == null) {
            throw new ApplicationException(PmsErrorCode.USER_BEHAVIOR_CONFIG_NULL);
        }
        int year = Integer.valueOf(DateUtil.getFormatDate(sf.getDate(), "yyyy"));
        Date[] dd = getStatDateRange(sf.getDateGroupType(), sf.getDate());
        UserBehavior userBehavior = baseService.getObject(UserBehavior.class, sf.getUserBehaviorId());
        List<UserBehaviorDataStat> list = userBehaviorService.statUserBehaviorData(userBehavior, dd[0], dd[1], sf.getUserId(), sf.getName());
        ChartCalendarData chartData = new ChartCalendarData();
        chartData.setTitle(year + "年用户行为(" + userBehavior.getTitle() + ")分析");
        chartData.setCount(list.size());
        chartData.setYear(year);
        chartData.setLegendData(userBehavior.getTitle(), 3);
        chartData.setUnit(userBehavior.getUserBehaviorConfig().getUnit());
        for (UserBehaviorDataStat stat : list) {
            double vv = Double.valueOf(stat.getValue().toString());
            //这时value就是天数值
            chartData.addSerieData(DateUtil.getFormatDate(stat.getDate(), DateUtil.FormatDay1), vv, stat.getDateRegion(), Integer.valueOf(stat.getValue().toString()));
        }
        return chartData;
    }

    /**
     * 用户行为统计（带饼图分析）
     *
     * @return
     */
    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public ResultBean compare(@Valid UserBehaviorCompareSearch sf) {
        Date startDate = DateUtil.getDate(sf.getYear() + "-01-01 00:00:00", DateUtil.Format24Datetime);
        Date endDate = DateUtil.getDate(sf.getYear() + "-12-31 23:59:59", DateUtil.Format24Datetime);
        ChartCalendarCompareData chartData = new ChartCalendarCompareData();
        chartData.setTitle("用户行为对比");
        chartData.setUnit("");
        chartData.setYear(sf.getYear());
        UserBehavior sourceUserBehavior = baseService.getObject(UserBehavior.class, sf.getSourceUserBehaviorId());
        List<UserBehaviorDataStat> sourceList = userBehaviorService.statUserBehaviorData(sourceUserBehavior, startDate, endDate, sf.getUserId(), sf.getSourceName());
        for (UserBehaviorDataStat stat : sourceList) {
            double vv = Double.valueOf(stat.getValue().toString());
            chartData.addSerieData(DateUtil.getFormatDate(stat.getDate(), DateUtil.FormatDay1), vv, stat.getDateRegion(), Integer.valueOf(stat.getValue().toString()), 1);
        }
        List<ChartCalendarCompareRowData> comapreRowDatas = new ArrayList<>();
        UserBehavior targetUserBehavior = baseService.getObject(UserBehavior.class, sf.getTargetUserBehaviorId());
        List<UserBehaviorDataStat> targetList = userBehaviorService.statUserBehaviorData(targetUserBehavior, startDate, endDate, sf.getUserId(), sf.getTargetName());
        for (UserBehaviorDataStat stat : targetList) {
            double vv = Double.valueOf(stat.getValue().toString());
            chartData.addSerieData(DateUtil.getFormatDate(stat.getDate(), DateUtil.FormatDay1), vv, stat.getDateRegion(), Integer.valueOf(stat.getValue().toString()), 2);
            ChartCalendarCompareRowData rowData = this.getCompareDate(DateUtil.getFormatDate(stat.getDate(), DateUtil.FormatDay1), sourceList);
            comapreRowDatas.add(rowData);
        }
        chartData.setCustomData(comapreRowDatas);
        String s1 = sourceUserBehavior.getTitle();
        if (sf.getSourceName() != null) {
            s1 += "(" + sf.getSourceName() + ")";
        }
        String s2 = targetUserBehavior.getTitle();
        if (sf.getTargetName() != null) {
            s2 += "(" + sf.getTargetName() + ")";
        }
        chartData.setLegendData(new String[]{s1, s2});
        return callback(chartData);
    }

    private ChartCalendarCompareRowData getCompareDate(String targetDate, List<UserBehaviorDataStat> sourceList) {
        ChartCalendarCompareRowData rowData = new ChartCalendarCompareRowData();
        Date t = DateUtil.getDate(targetDate, DateUtil.FormatDay1);
        for (UserBehaviorDataStat stat : sourceList) {
            Date s = DateUtil.getDate(stat.getDate(), DateUtil.FormatDay1);
            int days = DateUtil.getIntervalDays(s, t);
            if (days >= 0 && days <= 3) {
                rowData.setSourceDate(DateUtil.getFormatDate(stat.getDate(), DateUtil.FormatDay1));
                rowData.setDays(days);
            }
        }
        rowData.setTargetDate(targetDate);
        return rowData;
    }

    /**
     * 获取用户行为配置的关键字列表
     *
     * @return
     */
    @RequestMapping(value = "/getUserBehaviorKeywordsTree")
    public ResultBean getUserBehaviorKeywordsTree(Long id, Boolean needRoot) {
        try {
            List<String> dataList = userBehaviorService.getUserBehaviorKeywordsList(id);
            Set<String> keywordsSet = TreeBeanUtil.deleteDuplicate(dataList);
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (String ss : keywordsSet) {
                TreeBean tb = new TreeBean();
                tb.setId(ss);
                tb.setText(ss);
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取用户行为配置的关键字列表异常",
                    e);
        }
    }


}
